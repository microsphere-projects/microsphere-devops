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
package io.microsphere.nacos.client.constants;

import io.microsphere.nacos.client.transport.OpenApiRequest;

import static java.lang.Integer.getInteger;
import static java.lang.System.getProperty;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * The Constants
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiRequest
 * @since 1.0.0
 */
public interface Constants {

    /**
     * The property name prefix : "microsphere.nacos.client."
     */
    String PROPERTY_NAME_PREFIX = "microsphere.nacos.client.";

    /**
     * The property name of the maximum number of connections for Nacos Client: "microsphere.nacos.client.max-connections"
     */
    String MAX_CONNECTIONS_PROPERTY_NAME = PROPERTY_NAME_PREFIX + "max-connections";

    /**
     * The property name of the maximum number of connections per route for Nacos Client: "microsphere.nacos.client.max-per-route-connections"
     */
    String MAX_PER_ROUTE_CONNECTIONS_PROPERTY_NAME = PROPERTY_NAME_PREFIX + "max-per-route-connections";

    /**
     * The property name of the connection timeout for Nacos Client: "microsphere.nacos.client.connection-timeout"
     */
    String CONNECTION_TIMEOUT_PROPERTY_NAME = PROPERTY_NAME_PREFIX + "connection-timeout";

    /**
     * The property name of the read timeout for Nacos Client: "microsphere.nacos.client.read-timeout"
     */
    String READ_TIMEOUT_PROPERTY_NAME = PROPERTY_NAME_PREFIX + "read-timeout";

    /**
     * The property name of the long polling timeout for Nacos Client: "microsphere.nacos.client.long-polling-timeout"
     */
    String LONG_POLL_TIMEOUT_PROPERTY_NAME = PROPERTY_NAME_PREFIX + "long-polling-timeout";

    /**
     * The property name of the file encoding: "microsphere.nacos.client.encoding"
     */
    String ENCODING_PROPERTY_NAME = PROPERTY_NAME_PREFIX + "encoding";

    /**
     * The property name of the page number for Nacos Client: "microsphere.nacos.client.page-number"
     */
    String PAGE_NUMBER_PROPERTY_NAME = PROPERTY_NAME_PREFIX + "page-number";

    /**
     * The property name of the page size for Nacos Client: "microsphere.nacos.client.page-size"
     */
    String PAGE_SIZE_PROPERTY_NAME = PROPERTY_NAME_PREFIX + "page-size";

    /**
     * The default value of the maximum number of connections for Nacos Client : 1000
     */
    int DEFAULT_MAX_CONNECTIONS = 1000;

    /**
     * The default value of the maximum number of connections per route for Nacos Client : 200
     */
    int DEFAULT_MAX_PER_ROUTE_CONNECTIONS = 200;

    /**
     * The default value of the connection timeout for Nacos Client : 1 minute
     */
    int DEFAULT_CONNECTION_TIMEOUT = (int) MINUTES.toMillis(1);

    /**
     * The default value of the read timeout for Nacos Client : 3 minutes
     */
    int DEFAULT_READ_TIMEOUT = (int) MINUTES.toMillis(3);

    /**
     * The default value of the long polling timeout for Nacos Client : 30 seconds
     */
    int DEFAULT_LONG_POLLING_TIMEOUT = (int) SECONDS.toMicros(30);

    /**
     * The default value of the file encoding : "UTF-8"
     */
    String DEFAULT_ENCODING = "UTF-8";

    /**
     * Default page number : 1
     */
    int DEFAULT_PAGE_NUMBER = 1;

    /**
     * Default page size : 10
     */
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * The maximum number of connections for Nacos Client
     *
     * @see #DEFAULT_MAX_CONNECTIONS
     */
    int MAX_CONNECTIONS = getInteger(MAX_CONNECTIONS_PROPERTY_NAME, DEFAULT_MAX_CONNECTIONS);

    /**
     * The maximum number of connections per route for Nacos Client
     *
     * @see #DEFAULT_MAX_PER_ROUTE_CONNECTIONS
     */
    int MAX_PER_ROUTE_CONNECTIONS = getInteger(MAX_PER_ROUTE_CONNECTIONS_PROPERTY_NAME, DEFAULT_MAX_PER_ROUTE_CONNECTIONS);

    /**
     * The connection timeout for Nacos Client
     *
     * @see #DEFAULT_CONNECTION_TIMEOUT
     */
    int CONNECTION_TIMEOUT = getInteger(CONNECTION_TIMEOUT_PROPERTY_NAME, DEFAULT_CONNECTION_TIMEOUT);

    /**
     * The read timeout for Nacos Client
     *
     * @see #DEFAULT_READ_TIMEOUT
     */
    int READ_TIMEOUT = getInteger(READ_TIMEOUT_PROPERTY_NAME, DEFAULT_READ_TIMEOUT);

    /**
     * The long polling timeout for Nacos Client
     *
     * @see #DEFAULT_LONG_POLLING_TIMEOUT
     */
    int LONG_POLLING_TIMEOUT = getInteger(LONG_POLL_TIMEOUT_PROPERTY_NAME, DEFAULT_LONG_POLLING_TIMEOUT);

    /**
     * The encoding for Nacos Client
     */
    String ENCODING = getProperty(ENCODING_PROPERTY_NAME, DEFAULT_ENCODING);

    /**
     * The page number for Nacos Client, the default value is 1
     *
     * @see #DEFAULT_PAGE_NUMBER
     */
    int PAGE_NUMBER = getInteger(PAGE_NUMBER_PROPERTY_NAME, DEFAULT_PAGE_NUMBER);

    /**
     * The page size for Nacos Client, the default value is 10
     *
     * @see #DEFAULT_PAGE_SIZE
     */
    int PAGE_SIZE = getInteger(PAGE_SIZE_PROPERTY_NAME, DEFAULT_PAGE_SIZE);

    /**
     * The default namespace ID : "public"
     */
    String DEFAULT_NAMESPACE_ID = "public";

    /**
     * The default group name : "DEFAULT_GROUP"
     */
    String DEFAULT_GROUP_NAME = "DEFAULT_GROUP";

    /**
     * The default cluster name : "DEFAULT"
     */
    String DEFAULT_CLUSTER_NAME = "DEFAULT";

    /**
     * the separator for service name between raw service name and group.
     */
    String GROUP_SERVICE_NAME_SEPARATOR = "@@";


    // Open API Request Parameters

    /**
     * The request parameter name of Nacos Authentication username : "username"
     */
    String USER_NAME_PARAM_NAME = "username";

    /**
     * The request parameter name of Nacos Authentication password : "password"
     */
    String PASSWORD_PARAM_NAME = "password";

    /**
     * The request parameter name of Nacos Authentication access token : "accessToken"
     */
    String ACCESS_TOKEN_PARAM_NAME = "accessToken";

    /**
     * The request parameter name of the id of parameter for Nacos: "namespaceId"
     */
    String NAMESPACE_ID_PARAM_NAME = "namespaceId";

    /**
     * The request parameter name of Nacos Discovery's group name : "groupName"
     */
    String GROUP_NAME_PARAM_NAME = "groupName";

    /**
     * The request parameter name of Nacos Discovery's service name : "serviceName"
     */
    String SERVICE_NAME_PARAM_NAME = "serviceName";

    /**
     * The request parameter name of Nacos Discovery's service name : "clusterName"
     */
    String CLUSTER_NAME_PARAM_NAME = "clusterName";

    /**
     * The request parameter name of "tenant" a.k.a id of Nacos configuration's namespace: "tenant"
     */
    String CONFIG_TENANT_PARAM_NAME = "tenant";

    /**
     * The request parameter name of Nacos configuration's group : "group"
     */
    String CONFIG_GROUP_PARAM_NAME = "group";

    /**
     * The request parameter name of Nacos configuration's dataId : "dataId"
     */
    String CONFIG_DATA_ID_PARAM_NAME = "dataId";

    /**
     * The request parameter name of Nacos "show" details : "show"
     */
    String SHOW_PARAM_NAME = "show";

    /**
     * The request parameter name of Nacos configuration's content : "content"
     */
    String CONFIG_CONTENT_PARAM_NAME = "content";

    /**
     * The request parameter name of Nacos configuration's tag : "tag"
     */
    String TAG_PARAM_NAME = "tag";

    /**
     * The request parameter name of Nacos configuration's application name: "appName"
     */
    String APP_NAME_PARAM_NAME = "appName";

    /**
     * The request parameter name of Nacos configuration's operator: "src_user"
     */
    String OPERATOR_PARAM_NAME = "src_user";

    /**
     * The request parameter name of Nacos configuration's tags: "config_tags"
     */
    String CONFIG_TAGS_PARAM_NAME = "config_tags";

    /**
     * The request parameter name of Nacos configuration's description : "desc"
//     */
    String DESCRIPTION_PARAM_NAME = "desc";

    /**
     * The request parameter name of Nacos configuration's use : "use"
     */
    String CONFIG_USE_PARAM_NAME = "use";

    /**
     * The request parameter name of Nacos configuration's effect : "effect"
     */
    String CONFIG_EFFECT_PARAM_NAME = "effect";

    /**
     * The request parameter name of "Nacos configuration's type : "type"
     */
    String CONFIG_TYPE_PARAM_NAME = "type";

    /**
     * The request parameter name of Nacos configuration's schema : "schema"
     */
    String CONFIG_SCHEMA_PARAM_NAME = "schema";

    /**
     * The request parameter name of Nacos configuration's history search: "search"
     */
    String SEARCH_PARAM_NAME = "search";

    /**
     * The request parameter value of Nacos configuration's history search: "accurate"
     */
    String SEARCH_PARAM_VALUE = "accurate";

    /**
     * The request parameter name of Nacos page number : "pageNo"
     */
    String PAGE_NUMBER_PARAM_NAME = "pageNo";

    /**
     * The request parameter name of Nacos page size : "pageSize"
     */
    String PAGE_SIZE_PARAM_NAME = "pageSize";

    /**
     * The request parameter name of Nacos configuration's history revision : "nid"
     */
    String CONFIG_REVISION_PARAM_NAME = "nid";

    /**
     * The request parameter name of Nacos configuration's id : "id"
     */
    String CONFIG_ID_PARAM_NAME = "id";
}
