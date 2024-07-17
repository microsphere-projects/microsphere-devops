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

import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.config.model.Config;
import io.microsphere.nacos.client.v1.config.model.HistoryConfig;
import io.microsphere.nacos.client.v1.config.model.HistoryConfigPage;
import io.microsphere.nacos.client.v1.config.model.NewConfig;

import static io.microsphere.nacos.client.http.HttpMethod.DELETE;
import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.util.StringUtils.collectionToCommaDelimitedString;

/**
 * The {@link ConfigClient} for for <a href="https://nacos.io/en/docs/v1/open-api/#configuration-management">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ConfigClient
 * @since 1.0.0
 */
public class OpenApiConfigClient implements ConfigClient {

    private static final String CONFIG_ENDPOINT = "/v1/cs/configs";

    private static final String HISTORY_ENDPOINT = "/v1/cs/history";

    /**
     * The request parameter name of "tenant" a.k.a id of "namespace" for Nacos configuration: "tenant"
     */
    private static final String TENANT_PARAM_NAME = "tenant";

    /**
     * The request parameter name of "group" for Nacos configuration: "group"
     */
    private static final String GROUP_PARAM_NAME = "group";

    /**
     * The request parameter name of "dataId" for Nacos configuration: "dataId"
     */
    private static final String DATA_ID_PARAM_NAME = "dataId";

    /**
     * The request parameter name of "show" details for Nacos configuration: "show"
     */
    private static final String SHOW_PARAM_NAME = "show";

    /**
     * The request parameter name of content for Nacos configuration: "content"
     */
    private static final String CONTENT_PARAM_NAME = "content";

    /**
     * The request parameter name of "tag" for Nacos configuration: "tag"
     */
    private static final String TAG_PARAM_NAME = "tag";

    /**
     * The request parameter name of application name of Nacos configuration: "appName"
     */
    private static final String APP_NAME_PARAM_NAME = "appName";

    /**
     * The request parameter name of "operator" for Nacos configuration: "src_user"
     */
    private static final String OPERATOR_PARAM_NAME = "src_user";

    /**
     * The request parameter name of "tags" for Nacos configuration: "config_tags"
     */
    private static final String TAGS_PARAM_NAME = "config_tags";

    /**
     * The request parameter name of "description" for Nacos configuration: "desc"
     */
    private static final String DESCRIPTION_PARAM_NAME = "desc";

    /**
     * The request parameter name of "use" for Nacos configuration: "use"
     */
    private static final String USE_PARAM_NAME = "use";

    /**
     * The request parameter name of "effect" for Nacos configuration: "effect"
     */
    private static final String EFFECT_PARAM_NAME = "effect";

    /**
     * The request parameter name of "type" for Nacos configuration: "type"
     */
    private static final String TYPE_PARAM_NAME = "type";

    /**
     * The request parameter name of "schema" for Nacos configuration: "schema"
     */
    private static final String SCHEMA_PARAM_NAME = "schema";

    /**
     * The request parameter name of history for Nacos configuration: "search"
     */
    private static final String SEARCH_PARAM_NAME = "search";

    /**
     * The request parameter value of history for Nacos configuration: "accurate"
     */
    private static final String SEARCH_PARAM_VALUE = "accurate";

    /**
     * The request parameter name of history for Nacos configuration: "pageNo"
     */
    private static final String PAGE_NUMBER_PARAM_NAME = "pageNo";

    /**
     * The request parameter name of history for Nacos configuration: "pageSize"
     */
    private static final String PAGE_SIZE_PARAM_NAME = "pageSize";

    /**
     * The request parameter name of history revision for Nacos configuration : "nid"
     */
    private static final String REVISION_PARAM_NAME = "nid";

    private final OpenApiClient openApiClient;

    public OpenApiConfigClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    @Override
    public String getConfigContent(String namespaceId, String group, String dataId) {
        OpenApiRequest request = buildGetConfigRequest(namespaceId, group, dataId, false);
        return openApiClient.execute(request, String.class);
    }

    @Override
    public Config getConfig(String namespaceId, String group, String dataId) {
        OpenApiRequest request = buildGetConfigRequest(namespaceId, group, dataId, false);
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
        OpenApiRequest request = configRequestBuilder(namespaceId, group, dataId, POST).queryParameter(CONTENT_PARAM_NAME, content).queryParameter(TAGS_PARAM_NAME, tags).queryParameter(APP_NAME_PARAM_NAME, appName).queryParameter(OPERATOR_PARAM_NAME, operator).queryParameter(DESCRIPTION_PARAM_NAME, description).queryParameter(USE_PARAM_NAME, use).queryParameter(EFFECT_PARAM_NAME, effect).queryParameter(SCHEMA_PARAM_NAME, schema).queryParameter(TYPE_PARAM_NAME, type).build();
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
                .queryParameter(SEARCH_PARAM_NAME, SEARCH_PARAM_VALUE)
                .queryParameter(PAGE_NUMBER_PARAM_NAME, pageNumber)
                .queryParameter(PAGE_SIZE_PARAM_NAME, pageSize)
                .build();

        HistoryConfigPage page = this.openApiClient.execute(request, HistoryConfigPage.class);
        page.setPageNumber(pageNumber);
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    public HistoryConfig getHistoryConfig(String namespaceId, String group, String dataId, long revision) {
        OpenApiRequest request = historyConfigRequestBuilder(namespaceId, group, dataId, GET)
                .queryParameter(REVISION_PARAM_NAME, revision)
                .build();
        return this.openApiClient.execute(request, HistoryConfig.class);
    }

    @Override
    public HistoryConfig getPreviousHistoryConfig(String namespaceId, String group, String dataId, long id) {
        return null;
    }

    private OpenApiRequest buildGetConfigRequest(String namespaceId, String group, String dataId, boolean showDetails) {
        return configRequestBuilder(namespaceId, group, dataId, GET)
                .queryParameter(SHOW_PARAM_NAME, showDetails ? "all" : null)
                .build();
    }

    private OpenApiRequest.Builder historyConfigRequestBuilder(String namespaceId, String group, String dataId, HttpMethod method) {
        return requestBuilder(HISTORY_ENDPOINT, namespaceId, group, dataId, method);
    }

    private OpenApiRequest.Builder configRequestBuilder(String namespaceId, String group, String dataId, HttpMethod method) {
        return requestBuilder(CONFIG_ENDPOINT, namespaceId, group, dataId, method);
    }

    private OpenApiRequest.Builder requestBuilder(String endpoint, String namespaceId, String group, String dataId, HttpMethod method) {
        return OpenApiRequest.Builder.create(endpoint).method(method).queryParameter(TENANT_PARAM_NAME, namespaceId).queryParameter(GROUP_PARAM_NAME, group).queryParameter(DATA_ID_PARAM_NAME, dataId);
    }

    private boolean responseBoolean(OpenApiRequest request) {
        return this.openApiClient.execute(request, boolean.class);
    }
}
