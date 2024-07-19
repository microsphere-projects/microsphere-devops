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
import io.microsphere.nacos.client.v1.config.event.ConfigChangedEvent;
import io.microsphere.nacos.client.v1.config.event.ConfigChangedListener;
import io.microsphere.nacos.client.v1.config.model.Config;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static io.microsphere.nacos.client.v1.config.util.ConfigUtil.buildConfigId;

/**
 * The Manager class of {@link Config}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Config
 * @since 1.0.0
 */
class ConfigManager {

    private static final String LISTENER_ENDPOINT = "/v1/cs/configs/listener";

    private final ConfigClient configClient;

    private final NacosClientConfig nacosClientConfig;

    private final ScheduledExecutorService configChangedEventScheduler;

    private final ConcurrentMap<String, ConfigChangedListeners> configChangedListenersCache;

    ConfigManager(ConfigClient configClient, NacosClientConfig nacosClientConfig) {
        this.configClient = configClient;
        this.nacosClientConfig = nacosClientConfig;
        this.configChangedEventScheduler = initConfigChangedEventScheduler();
        this.configChangedListenersCache = new ConcurrentHashMap<>();
    }

    void addEventListener(String namespaceId, String group, String dataId, ConfigChangedListener listener) {
        handleEventListener(namespaceId, group, dataId, listener, false);
    }

    void removeEventListener(String namespaceId, String group, String dataId, ConfigChangedListener listener) {
        handleEventListener(namespaceId, group, dataId, listener, true);
    }


    private void handleEventListener(String namespaceId, String group, String dataId, ConfigChangedListener listener,
                                     boolean forRemoval) {
        String configId = buildConfigId(namespaceId, group, dataId);
        ConfigChangedListeners configChangedListeners = getConfigChangedListeners(configId);
        if (forRemoval) {
            configChangedListeners.removeListener(listener);
        } else {
            configChangedListeners.addListener(listener);
        }
        Config config = getConfig(namespaceId, group, dataId);
        if (config == null) { // The config is not existed now

        } else {

        }

    }

    private ScheduledExecutorService initConfigChangedEventScheduler() {
        return Executors.newSingleThreadScheduledExecutor(task -> {
            Thread thread = new Thread(task, "Nacos Client - ConfigChangedEvent Scheduler");
            thread.setDaemon(true);
            return thread;
        });
    }


    private ConfigChangedListeners getConfigChangedListeners(String configId) {
        return configChangedListenersCache.computeIfAbsent(configId, k -> new ConfigChangedListeners());
    }

    private Config getConfig(String namespaceId, String group, String dataId) {
        return this.configClient.getConfig(namespaceId, group, dataId);
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
