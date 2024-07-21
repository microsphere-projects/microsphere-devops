/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microsphere.nacos.client.v1.config;

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.config.event.ConfigChangedEvent;
import io.microsphere.nacos.client.v1.config.event.ConfigChangedListener;
import io.microsphere.nacos.client.v1.config.model.Config;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static io.microsphere.nacos.client.constants.Constants.LISTENING_CONFIG_SEPARATOR;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.transport.OpenApiRequestHeader.LONG_PULLING_TIMEOUT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.LISTENING_CONFIGS;
import static io.microsphere.nacos.client.v1.config.event.ConfigChangedEvent.ofCreated;
import static io.microsphere.nacos.client.v1.config.event.ConfigChangedEvent.ofDeleted;
import static io.microsphere.nacos.client.v1.config.event.ConfigChangedEvent.ofModified;
import static io.microsphere.nacos.client.v1.config.util.ConfigUtil.buildConfigId;
import static io.microsphere.nacos.client.v1.config.util.ConfigUtil.buildListeningConfigDataPacket;
import static java.lang.Runtime.getRuntime;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

/**
 * The Manager class of {@link Config} Listener
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Config
 * @since 1.0.0
 */
class ConfigListenerManager {

    private static final String LISTENER_ENDPOINT = "/v1/cs/configs/listener";

    private final ConfigClient configClient;

    private final OpenApiClient openApiClient;

    private final NacosClientConfig nacosClientConfig;

    private final ConcurrentMap<String, ListeningConfig> listeningConfigsCache;

    private final CopyOnWriteArraySet<String> fetchingConfigIds;

    private final CopyOnWriteArraySet<String> listeningConfigDataPackets;

    private final ExecutorService fetchingConfigsExecutor;

    private final ScheduledExecutorService listeningConfigsScheduler;

    ConfigListenerManager(ConfigClient configClient, OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        this.configClient = configClient;
        this.openApiClient = openApiClient;
        this.nacosClientConfig = nacosClientConfig;
        this.listeningConfigsCache = new ConcurrentHashMap<>();
        this.fetchingConfigIds = new CopyOnWriteArraySet();
        this.listeningConfigDataPackets = new CopyOnWriteArraySet();
        this.fetchingConfigsExecutor = initFetchingConfigsExecutor();
        this.listeningConfigsScheduler = initListeningConfigsScheduler();
        // Add shutdown hook
        getRuntime().addShutdownHook(new Thread(this::destroy));
    }

    private void destroy() {
        this.fetchingConfigsExecutor.shutdown();
        this.listeningConfigsScheduler.shutdown();
        this.listeningConfigDataPackets.clear();
        this.fetchingConfigIds.clear();
        this.listeningConfigsCache.clear();
    }

    void addEventListener(String namespaceId, String group, String dataId, ConfigChangedListener listener) {
        handleEventListener(namespaceId, group, dataId, listener, false);
    }

    void removeEventListener(String namespaceId, String group, String dataId, ConfigChangedListener listener) {
        handleEventListener(namespaceId, group, dataId, listener, true);
    }


    private void handleEventListener(String namespaceId, String group, String dataId, ConfigChangedListener listener, boolean forRemoval) {

        ListeningConfig listeningConfig = getListeningConfig(namespaceId, group, dataId);
        listeningConfig.handleListener(listener, forRemoval);

        Config config = listeningConfig.config;

        if (config == null) {
            // The specified config is not existed now,
            // it will be fetched in next scheduled task
            fetchingConfigIds.add(listeningConfig.configId);
            synchronized (fetchingConfigIds) {
                fetchingConfigIds.notifyAll();
            }
        } else {
            listeningConfig.addDataPacket(config);
        }
    }

    private ListeningConfig getListeningConfig(String namespaceId, String group, String dataId) {
        String configId = buildConfigId(namespaceId, group, dataId);
        return this.listeningConfigsCache.computeIfAbsent(configId, id -> new ListeningConfig(namespaceId, group, dataId, configId));
    }

    private ExecutorService initFetchingConfigsExecutor() {
        ExecutorService executorService = newSingleThreadExecutor(task -> {
            Thread thread = new Thread(task, "Nacos Client - Fetching Config Executor");
            thread.setDaemon(true);
            return thread;
        });

        executorService.execute(this::fetchConfigs);
        return executorService;
    }

    private ScheduledExecutorService initListeningConfigsScheduler() {
        ScheduledExecutorService scheduledExecutor = newSingleThreadScheduledExecutor(task -> {
            Thread thread = new Thread(task, "Nacos Client - Listening Config Scheduler");
            thread.setDaemon(true);
            return thread;
        });
        int longPollingTimeout = nacosClientConfig.getLongPollingTimeout();
        scheduledExecutor.scheduleAtFixedRate(this::listen, longPollingTimeout, longPollingTimeout, TimeUnit.MILLISECONDS);
        return scheduledExecutor;
    }


    private void fetchConfigs() {
        while (true) {
            Collection<String> loadingConfigIds = this.fetchingConfigIds;
            synchronized (loadingConfigIds) {
                while (loadingConfigIds.isEmpty()) {
                    try {
                        loadingConfigIds.wait(); // Blocking if loadingConfigIds is empty
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            try {
                Iterator<String> iterator = loadingConfigIds.iterator();
                while (iterator.hasNext()) {
                    String configId = iterator.next();
                    ListeningConfig listeningConfig = listeningConfigsCache.get(configId);
                    Config config = listeningConfig.config;
                    if (config == null) { // try to fetch a new config
                        config = listeningConfig.fetch();
                    }
                    if (config != null) { // The config was found
                        listeningConfig.update(config);
                        iterator.remove();
                    }
                }
            } catch (Throwable e) {
                // Catch any exception
                // TODO Log
            }
        }

    }

    private boolean fetchAndUpdate(String configId) {
        ListeningConfig listeningConfig = listeningConfigsCache.get(configId);
        Config config = listeningConfig.fetch();
        boolean updated = listeningConfig.update(config);
        return updated;
    }


    private void listen() {
        try {
            String[] changedConfigIds = getChangedConfigIds();
            if (changedConfigIds != null) {
                for (String changedConfigId : changedConfigIds) {
                    fetchAndUpdate(changedConfigId);
                }
            }
        } catch (Throwable e) {
            // Catch any exception
            // TODO Log
        }
    }

    private String[] getChangedConfigIds() {
        String listeningConfigs = buildListeningConfigs();
        String[] changedConfigIds = null;

        if (listeningConfigs != null) {
            int longPollingTimeout = this.nacosClientConfig.getLongPollingTimeout();
            OpenApiRequest request = OpenApiRequest.Builder.create(LISTENER_ENDPOINT)
                    .method(POST)
                    .queryParameter(LISTENING_CONFIGS, listeningConfigs)
                    .header(LONG_PULLING_TIMEOUT, longPollingTimeout)
                    .build();
            String changedConfigIdsContent = this.openApiClient.execute(request, String.class);
            changedConfigIds = changedConfigIdsContent == null ? null : changedConfigIdsContent.split(LISTENING_CONFIG_SEPARATOR);
        }
        return changedConfigIds;
    }

    private String buildListeningConfigs() {
        if (listeningConfigDataPackets.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        listeningConfigDataPackets.forEach(builder::append);
        return builder.toString();
    }

    private Config getConfig(String namespaceId, String group, String dataId) {
        return this.configClient.getConfig(namespaceId, group, dataId);
    }

    class ListeningConfig {

        private final String namespaceId;

        private final String group;

        private final String dataId;

        private final String configId;

        private volatile Config config;

        private final ConfigChangedListeners listeners;

        ListeningConfig(String namespaceId, String group, String dataId, String configId) {
            this.namespaceId = namespaceId;
            this.group = group;
            this.dataId = dataId;
            this.configId = configId;
            this.config = fetch();
            this.listeners = new ConfigChangedListeners();
        }


        void handleListener(ConfigChangedListener listener, boolean forRemoval) {
            if (forRemoval) {
                listeners.removeListener(listener);
            } else {
                listeners.addListener(listener);
            }
        }

        Config fetch() {
            return ConfigListenerManager.this.getConfig(this.namespaceId, this.group, this.dataId);
        }

        /**
         * Update a new {@link Config} and fire the event if needed
         *
         * @param config a new {@link Config}
         * @return {@code true} if the {@link Config} was changed, {@code false} otherwise
         */
        boolean update(Config config) {
            Config previousConfig = this.config;
            if (previousConfig == null) {
                if (config != null) {
                    // The Config was created in first time
                    listeners.onEvent(ofCreated(config));
                } else {
                    // otherwise does nothing
                    return false;
                }
            } else {
                if (config == null) {
                    // The Config was deleted
                    listeners.onEvent(ofDeleted(previousConfig));
                } else {
                    if (Objects.equals(previousConfig.getMd5(), config.getMd5())) {
                        return false;
                    }
                    // The Config was modified
                    listeners.onEvent(ofModified(previousConfig, config));
                }
                // Remove the previous Data Package
                removeDataPacket(previousConfig);
            }

            if (config != null) {
                // Add new Data Package
                addDataPacket(config);
            }

            this.config = config;
            return true;
        }

        private void addDataPacket(Config config) {
            String md5 = config.getMd5();
            String listenerConfigId = buildListeningConfigDataPacket(namespaceId, group, dataId, md5);
            listeningConfigDataPackets.add(listenerConfigId);
        }

        private void removeDataPacket(Config config) {
            String md5 = config.getMd5();
            String listenerConfigId = buildListeningConfigDataPacket(namespaceId, group, dataId, md5);
            listeningConfigDataPackets.remove(listenerConfigId);
        }
    }

    static class ConfigChangedListeners implements ConfigChangedListener {

        private final CopyOnWriteArrayList<ConfigChangedListener> listeners;

        ConfigChangedListeners() {
            this.listeners = new CopyOnWriteArrayList<>();
        }

        @Override
        public void onEvent(ConfigChangedEvent event) {
            listeners.forEach(listener -> listener.onEvent(event));
        }

        public void addListener(ConfigChangedListener listener) {
            this.listeners.add(listener);
        }

        public void removeListener(ConfigChangedListener listener) {
            this.listeners.remove(listener);
        }
    }
}
