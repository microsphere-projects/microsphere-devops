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
package io.microsphere.nacos.client;

import java.io.Serializable;
import java.util.Objects;

import static io.microsphere.nacos.client.constants.Constants.CONNECTION_TIMEOUT;
import static io.microsphere.nacos.client.constants.Constants.ENCODING;
import static io.microsphere.nacos.client.constants.Constants.LONG_POLLING_TIMEOUT;
import static io.microsphere.nacos.client.constants.Constants.MAX_CONNECTIONS;
import static io.microsphere.nacos.client.constants.Constants.MAX_PER_ROUTE_CONNECTIONS;
import static io.microsphere.nacos.client.constants.Constants.READ_TIMEOUT;

/**
 * The Nacos Client Config
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
public class NacosClientConfig implements Serializable {

    private static final long serialVersionUID = 4986040001466414402L;

    /**
     * The Nacos naming server address
     */
    private String serverAddress;

    /**
     * The Nacos Open API service scheme
     */
    private String scheme = "http";

    /**
     * The Nacos Open API service context path
     */
    private String contextPath = "/nacos";

    /**
     * The Nacos authentication username
     */
    private String username;

    /**
     * The Nacos authentication password
     */
    private String password;

    /**
     * The access key for namespace.
     */
    private String accessKey;

    /**
     * The secret key for namespace.
     */
    private String secretKey;

    /**
     * The maximum number of connections for Nacos Client
     */
    private int maxConnections = MAX_CONNECTIONS;

    /**
     * The maximum number of connections per route for Nacos Client
     */
    private int maxPerRoute = MAX_PER_ROUTE_CONNECTIONS;

    /**
     * The connection timeout for Nacos Client
     */
    private int connectionTimeout = CONNECTION_TIMEOUT;

    /**
     * The read timeout for Nacos Client
     */
    private int readTimeout = READ_TIMEOUT;

    /**
     * The long polling timeout for Nacos Client
     */
    private int longPollingTimeout = LONG_POLLING_TIMEOUT;

    /**
     * The encoding for Nacos Client
     */
    private String encoding = ENCODING;

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getLongPollingTimeout() {
        return longPollingTimeout;
    }

    public void setLongPollingTimeout(int longPollingTimeout) {
        this.longPollingTimeout = longPollingTimeout;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isAuthorizationEnabled() {
        return this.username != null && this.password != null;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NacosClientConfig that)) return false;

        return maxConnections == that.maxConnections && maxPerRoute == that.maxPerRoute && connectionTimeout == that.connectionTimeout && readTimeout == that.readTimeout && longPollingTimeout == that.longPollingTimeout && Objects.equals(serverAddress, that.serverAddress) && Objects.equals(scheme, that.scheme) && Objects.equals(contextPath, that.contextPath) && Objects.equals(username, that.username) && Objects.equals(password, that.password) && Objects.equals(accessKey, that.accessKey) && Objects.equals(secretKey, that.secretKey) && Objects.equals(encoding, that.encoding);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(serverAddress);
        result = 31 * result + Objects.hashCode(scheme);
        result = 31 * result + Objects.hashCode(contextPath);
        result = 31 * result + Objects.hashCode(username);
        result = 31 * result + Objects.hashCode(password);
        result = 31 * result + Objects.hashCode(accessKey);
        result = 31 * result + Objects.hashCode(secretKey);
        result = 31 * result + maxConnections;
        result = 31 * result + maxPerRoute;
        result = 31 * result + connectionTimeout;
        result = 31 * result + readTimeout;
        result = 31 * result + longPollingTimeout;
        result = 31 * result + Objects.hashCode(encoding);
        return result;
    }

    @Override
    public String toString() {
        return "NacosClientConfig{" +
                "serverAddress='" + serverAddress + '\'' +
                ", scheme='" + scheme + '\'' +
                ", contextPath='" + contextPath + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", maxConnections=" + maxConnections +
                ", maxPerRoute=" + maxPerRoute +
                ", connectionTimeout=" + connectionTimeout +
                ", readTimeout=" + readTimeout +
                ", longPollingTimeout=" + longPollingTimeout +
                ", encoding='" + encoding + '\'' +
                '}';
    }
}
