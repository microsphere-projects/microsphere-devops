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
import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.config.event.ConfigChangedEvent;
import io.microsphere.nacos.client.v1.config.event.ConfigChangedListener;
import io.microsphere.nacos.client.v1.config.model.Config;
import io.microsphere.nacos.client.v1.config.model.HistoryConfig;
import io.microsphere.nacos.client.v1.config.model.HistoryConfigPage;
import io.microsphere.nacos.client.v1.config.model.NewConfig;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static io.microsphere.nacos.client.constants.Constants.LISTENING_CONFIG_FIELD_SEPARATOR_CHAR;
import static io.microsphere.nacos.client.constants.Constants.SEARCH_PARAM_VALUE;
import static io.microsphere.nacos.client.http.HttpMethod.DELETE;
import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.APP_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_CONTENT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_DATA_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_EFFECT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_GROUP;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_REVISION;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_SCHEMA;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_SEARCH;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TAGS;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TENANT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_TYPE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONFIG_USE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.DESCRIPTION;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.OPERATOR;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.PAGE_NUMBER;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.PAGE_SIZE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SHOW;
import static io.microsphere.nacos.client.util.StringUtils.collectionToCommaDelimitedString;
import static io.microsphere.nacos.client.v1.config.util.ConfigUtil.buildConfigId;

/**
 * The {@link ConfigClient} for for <a href="https://nacos.io/en/docs/v1/open-api/#configuration-management">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ConfigClient
 * @since 1.0.0
 */
public class OpenApiConfigClient implements ConfigClient {

    private static final String CONFIG_ENDPOINT = "/v1/cs/configs";

    private static final String CONFIG_HISTORY_ENDPOINT = "/v1/cs/history";

    private static final String CONFIG_HISTORY_PREVIOUS_ENDPOINT = "/v1/cs/history/previous";

    private final OpenApiClient openApiClient;

    private final NacosClientConfig nacosClientConfig;

    private final ScheduledExecutorService configChangedEventScheduler;

    private final ConcurrentMap<String, ConfigChangedListeners> configChangedListenersCache;

    public OpenApiConfigClient(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        this.openApiClient = openApiClient;
        this.nacosClientConfig = nacosClientConfig;
        this.configChangedEventScheduler = initConfigChangedEventScheduler();
        this.configChangedListenersCache = new ConcurrentHashMap<>();
    }

    @Override
    public String getConfigContent(String namespaceId, String group, String dataId) {
        OpenApiRequest request = buildGetConfigRequest(namespaceId, group, dataId, false);
        return openApiClient.execute(request, String.class);
    }

    @Override
    public Config getConfig(String namespaceId, String group, String dataId) {
        OpenApiRequest request = buildGetConfigRequest(namespaceId, group, dataId, true);
        return openApiClient.execute(request, Config.class);
    }

    @Override
    public boolean publishConfig(NewConfig newConfig) {
        String namespaceId = newConfig.getNamespaceId();
        String group = newConfig.getGroup();
        String dataId = newConfig.getDataId();
        String content = newConfig.getContent();
        String tags = collectionToCommaDelimitedString(newConfig.getTags());
        String appName = newConfig.getAppName();
        String operator = newConfig.getOperator();
        String description = newConfig.getDescription();
        String use = newConfig.getUse();
        String effect = newConfig.getEffect();
        String schema = newConfig.getSchema();
        ConfigType configType = newConfig.getType();
        String type = configType == null ? null : configType.getValue();
        OpenApiRequest request = configRequestBuilder(namespaceId, group, dataId, POST)
                .queryParameter(CONFIG_CONTENT, content)
                .queryParameter(CONFIG_TAGS, tags)
                .queryParameter(APP_NAME, appName)
                .queryParameter(OPERATOR, operator)
                .queryParameter(DESCRIPTION, description)
                .queryParameter(CONFIG_USE, use)
                .queryParameter(CONFIG_EFFECT, effect)
                .queryParameter(CONFIG_SCHEMA, schema)
                .queryParameter(CONFIG_TYPE, type).build();
        return responseBoolean(request);
    }

    @Override
    public boolean deleteConfig(String namespaceId, String group, String dataId) {
        OpenApiRequest request = configRequestBuilder(namespaceId, group, dataId, DELETE).build();
        return responseBoolean(request);
    }

    @Override
    public Page<HistoryConfig> getHistoryConfigs(String namespaceId, String group, String dataId, int pageNumber, int pageSize) {
        if (pageNumber < 1) {
            throw new IllegalArgumentException("The argument 'pageNumber' must be greater than 0");
        }
        if (pageSize < 1) {
            throw new IllegalArgumentException("The argument 'pageSize' must be greater than 0");
        }
        if (pageSize > MAX_PAGE_SIZE) {
            throw new IllegalArgumentException("The argument 'pageSize' must less than or equal 1");
        }

        OpenApiRequest request = historyConfigRequestBuilder(namespaceId, group, dataId, GET)
                .queryParameter(CONFIG_SEARCH, SEARCH_PARAM_VALUE)
                .queryParameter(PAGE_NUMBER, pageNumber)
                .queryParameter(PAGE_SIZE, pageSize)
                .build();

        HistoryConfigPage page = this.openApiClient.execute(request, HistoryConfigPage.class);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    public HistoryConfig getHistoryConfig(String namespaceId, String group, String dataId, long revision) {
        OpenApiRequest request = historyConfigRequestBuilder(namespaceId, group, dataId, GET)
                .queryParameter(CONFIG_REVISION, revision)
                .build();
        return responseHistoryConfig(request);
    }

    @Override
    public HistoryConfig getPreviousHistoryConfig(String namespaceId, String group, String dataId, String id) {
        OpenApiRequest request = requestBuilder(CONFIG_HISTORY_PREVIOUS_ENDPOINT, namespaceId, group, dataId, GET)
                .queryParameter(CONFIG_ID, id)
                .build();
        return responseHistoryConfig(request);
    }

    @Override
    public void addEventListener(String namespaceId, String group, String dataId, ConfigChangedListener listener) {
        handleEventListener(namespaceId, group, dataId, listener, false);
    }

    @Override
    public void removeEventListener(String namespaceId, String group, String dataId, ConfigChangedListener listener) {
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

        }
    }

    private ConfigChangedListeners getConfigChangedListeners(String configId) {
        return configChangedListenersCache.computeIfAbsent(configId, k -> new ConfigChangedListeners());
    }

    private OpenApiRequest buildGetConfigRequest(String namespaceId, String group, String dataId, boolean showDetails) {
        return configRequestBuilder(namespaceId, group, dataId, GET)
                .queryParameter(SHOW, showDetails ? "all" : null)
                .build();
    }

    private OpenApiRequest.Builder historyConfigRequestBuilder(String namespaceId, String group, String dataId, HttpMethod method) {
        return requestBuilder(CONFIG_HISTORY_ENDPOINT, namespaceId, group, dataId, method);
    }

    private OpenApiRequest.Builder configRequestBuilder(String namespaceId, String group, String dataId, HttpMethod method) {
        return requestBuilder(CONFIG_ENDPOINT, namespaceId, group, dataId, method);
    }

    private OpenApiRequest.Builder requestBuilder(String endpoint, String namespaceId, String group, String dataId, HttpMethod method) {
        return OpenApiRequest.Builder.create(endpoint).method(method)
                .queryParameter(CONFIG_TENANT, namespaceId)
                .queryParameter(CONFIG_GROUP, group)
                .queryParameter(CONFIG_DATA_ID, dataId);
    }

    private boolean responseBoolean(OpenApiRequest request) {
        return this.openApiClient.execute(request, boolean.class);
    }

    private HistoryConfig responseHistoryConfig(OpenApiRequest request) {
        return this.openApiClient.execute(request, HistoryConfig.class);
    }

    private ScheduledExecutorService initConfigChangedEventScheduler() {
        return Executors.newSingleThreadScheduledExecutor(task -> {
            Thread thread = new Thread(task, "Nacos Client - ConfigChangedEvent Scheduler");
            thread.setDaemon(true);
            return thread;
        });
    }



    static class ListeningConfigTask {


    }

    static class ConfigChangedListeners implements ConfigChangedListener {

        private final CopyOnWriteArrayList<ConfigChangedListener> listeners;

        public ConfigChangedListeners() {
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

