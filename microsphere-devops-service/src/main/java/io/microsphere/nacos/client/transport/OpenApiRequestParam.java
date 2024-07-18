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
package io.microsphere.nacos.client.transport;

import io.microsphere.nacos.client.v1.naming.ConsistencyType;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.microsphere.nacos.client.constants.Constants.ACCESS_TOKEN_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.APP_NAME_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CLUSTER_NAME_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_CONTENT_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_DATA_ID_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_EFFECT_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_GROUP_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_ID_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_REVISION_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_SCHEMA_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_SEARCH_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_TAGS_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_TENANT_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_TYPE_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.CONFIG_USE_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.DESCRIPTION_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.NAMESPACE_ID_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.OPERATOR_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.PAGE_NUMBER_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.PAGE_SIZE_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.PASSWORD_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.SERVICE_GROUP_NAME_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.SERVICE_NAME_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.SHOW_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.TAG_PARAM_NAME;
import static io.microsphere.nacos.client.constants.Constants.USER_NAME_PARAM_NAME;
import static io.microsphere.nacos.client.util.JsonUtils.toJSON;
import static io.microsphere.nacos.client.util.StringUtils.collectionToCommaDelimitedString;

/**
 * The enumeration for Open API Request Parameters
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiRequest
 * @see OpenApiHttpClient
 * @since 1.0.0
 */
public enum OpenApiRequestParam {

    USER_NAME(USER_NAME_PARAM_NAME),

    PASSWORD(PASSWORD_PARAM_NAME),

    ACCESS_TOKEN(ACCESS_TOKEN_PARAM_NAME),

    NAMESPACE_ID(NAMESPACE_ID_PARAM_NAME),

    /**
     * The request parameter of Nacos page number
     */
    PAGE_NUMBER(PAGE_NUMBER_PARAM_NAME),

    /**
     * The request parameter of Nacos page size
     */
    PAGE_SIZE(PAGE_SIZE_PARAM_NAME),

    /**
     * The request parameter of Nacos "show" details
     */
    SHOW(SHOW_PARAM_NAME),

    /**
     * The request parameter of Nacos application name
     */
    APP_NAME(APP_NAME_PARAM_NAME),

    /**
     * The request parameter of Nacos operator
     */
    OPERATOR(OPERATOR_PARAM_NAME),

    /**
     * The request parameter of Nacos description
     */
    DESCRIPTION(DESCRIPTION_PARAM_NAME),

    /**
     * The request parameter of Nacos consistency type
     */
    CONSISTENCY_TYPE("consistencyType", ConsistencyType.class) {
        @Override
        public String toValue(Object rawValue) {
            return ((ConsistencyType) rawValue).getValue();
        }
    },

    // Server

    /**
     * The request parameter of Nacos server switch entry
     */
    SERVER_SWITCH_ENTRY("entry"),

    /**
     * The request parameter of Nacos server switch value
     */
    SERVER_SWITCH_VALUE("value"),

    /**
     * The request parameter of Nacos server switch debug
     */
    SERVER_SWITCH_DEBUG("debug", boolean.class),

    // Namespace

    /**
     * The request parameter of Nacos Namespace's id
     *
     * @see #CUSTOM_NAMESPACE_ID
     * @see #NAMESPACE_ID
     */
    NAMESPACE("namespace"),

    /**
     * The request parameter of Nacos Namespace's custom namespace id
     */
    CUSTOM_NAMESPACE_ID("customNamespaceId"),

    /**
     * The request parameter of Nacos Namespace's name
     *
     * @see #NAMESPACE_NAME
     */
    NAMESPACE_SHOW_NAME("namespaceShowName"),

    /**
     * The request parameter of Nacos Namespace's name
     */
    NAMESPACE_NAME("namespaceName"),

    /**
     * The request parameter of Nacos Namespace's description
     */
    NAMESPACE_DESCRIPTION("namespaceDesc"),

    // Configuration

    /**
     * The request parameter of "tenant" a.k.a id of Nacos configuration's namespace
     *
     * @see #NAMESPACE_ID
     */
    CONFIG_TENANT(CONFIG_TENANT_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's history search
     */
    CONFIG_SEARCH(CONFIG_SEARCH_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's group
     */
    CONFIG_GROUP(CONFIG_GROUP_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's dataId
     */
    CONFIG_DATA_ID(CONFIG_DATA_ID_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's content
     */
    CONFIG_CONTENT(CONFIG_CONTENT_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's tag
     */
    CONFIG_TAG(TAG_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's tags
     */
    CONFIG_TAGS(CONFIG_TAGS_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's use
     */
    CONFIG_USE(CONFIG_USE_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's effect
     */
    CONFIG_EFFECT(CONFIG_EFFECT_PARAM_NAME),

    /**
     * The request parameter of "Nacos configuration's type
     */
    CONFIG_TYPE(CONFIG_TYPE_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's schema
     */
    CONFIG_SCHEMA(CONFIG_SCHEMA_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's history revision : "nid"
     */
    CONFIG_REVISION(CONFIG_REVISION_PARAM_NAME),

    /**
     * The request parameter of Nacos configuration's id : "id"
     */
    CONFIG_ID(CONFIG_ID_PARAM_NAME),

    // Discovery

    /**
     * The request parameter of Nacos Discovery's group name
     */
    SERVICE_GROUP_NAME(SERVICE_GROUP_NAME_PARAM_NAME),

    /**
     * The request parameter of Nacos Discovery's service name
     */
    SERVICE_NAME(SERVICE_NAME_PARAM_NAME),

    /**
     * The request parameter of Nacos Discovery's service protect threshold
     */
    SERVICE_PROTECT_THRESHOLD("protectThreshold", float.class),

    /**
     * The request parameter of Nacos Discovery's selector
     */
    SERVICE_SELECTOR("selector"),

    /**
     * The request parameter of Nacos Discovery's cluster
     */
    CLUSTER_NAME(CLUSTER_NAME_PARAM_NAME),

    /**
     * The request parameter of Nacos Discovery's clusters
     */
    CLUSTERS("clusters", Collection.class) {
        @Override
        public String toValue(Object rawValue) {
            return collectionToCommaDelimitedString((Collection) rawValue);
        }
    },

    METADATA("metadata", Map.class) {
        @Override
        public String toValue(Object rawValue) {
            return toJSON((Map<String, String>) rawValue);
        }
    },

    /**
     * The request parameter of Nacos Discovery's instance healthy only
     */
    INSTANCE_HEALTHY_ONLY("healthyOnly", boolean.class),

    /**
     * The request parameter of Nacos Discovery's instance healthy
     */
    INSTANCE_HEALTHY("healthy", boolean.class),

    /**
     * The request parameter of Nacos Discovery's instance IP
     */
    INSTANCE_IP("ip"),

    /**
     * The request parameter of Nacos Discovery's instance port
     */
    INSTANCE_PORT("port", int.class),

    /**
     * The request parameter of Nacos Discovery's instance weight
     */
    INSTANCE_WEIGHT("weight", Double.class),

    /**
     * The request parameter of Nacos Discovery's instance enabled status
     */
    INSTANCE_ENABLED("enabled", boolean.class),

    /**
     * The request parameter of Nacos Discovery's instance ephemeral status
     */
    INSTANCE_EPHEMERAL("ephemeral", Boolean.class),

    /**
     * The request parameter of Nacos Discovery's instances JSON
     */
    INSTANCES("instances", List.class) {
        @Override
        public String toValue(Object rawValue) {
            List<Map<String, String>> instances = (List<Map<String, String>>) rawValue;
            return toJSON(instances);
        }
    },
    ;
    /**
     * The HTTP request parameter name
     */
    private final String name;

    private final Class<?> type;

    OpenApiRequestParam(String name) {
        this(name, String.class);
    }

    OpenApiRequestParam(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    /**
     * The request parameter name
     *
     * @return non-null
     */
    public String getName() {
        return name;
    }

    /**
     * The request parameter type
     *
     * @return non-null
     */
    public Class<?> getType() {
        return type;
    }

    /**
     * Covert the raw parameter value to the parameter value of query string
     *
     * @param rawValue the raw parameter value (optional)
     * @return the parameter value of query string if the {@code rawValue} is not {@code null}
     */
    public String toValue(Object rawValue) {
        return rawValue == null ? null : rawValue.toString();
    }
}
