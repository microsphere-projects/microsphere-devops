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
     * The property name of the file encoding: "microsphere.nacos.client.encoding"
     */
    String ENCODING_PROPERTY_NAME = PROPERTY_NAME_PREFIX + "encoding";

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
     * The default value of the file encoding : "UTF-8"
     */
    String DEFAULT_ENCODING = "UTF-8";

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
     * The encoding for Nacos Client
     */
    String ENCODING = getProperty(ENCODING_PROPERTY_NAME, DEFAULT_ENCODING);

}
