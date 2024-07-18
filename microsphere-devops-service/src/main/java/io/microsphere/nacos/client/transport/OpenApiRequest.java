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

import io.microsphere.nacos.client.http.HttpMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNull;

/**
 * The Nacos request for Open API
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiRequestParam
 * @see OpenApiRequestHeader
 * @see OpenApiClient
 * @see OpenApiResponse
 * @since 1.0.0
 */
public class OpenApiRequest {

    private final String endpoint;

    private final HttpMethod method;

    private final Map<String, String> queryParameters;

    private final Map<String, String> headers;

    protected OpenApiRequest(String endpoint, HttpMethod method, Map<String, String> queryParameters, Map<String, String> headers) {
        requireNonNull(endpoint, "The 'endpoint' argument must not be null");
        this.endpoint = endpoint;
        this.method = method == null ? HttpMethod.GET : method;
        this.queryParameters = queryParameters;
        this.headers = headers;
    }

    /**
     * Returns the part of this request's URL from the protocol name up to the query string
     * in the first line of the HTTP request.
     *
     * @return non-null
     */
    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Get the HTTP method
     *
     * @return non-null
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * Get the parameters from the query string
     *
     * @return non-null
     */
    public Map<String, String> getQueryParameters() {
        Map<String, String> queryParameters = this.queryParameters;
        return queryParameters == null ? emptyMap() : unmodifiableMap(queryParameters);
    }

    @Override
    public String toString() {
        return this.method + " " + this.endpoint +
                System.lineSeparator() +
                "Query Params : " + this.queryParameters;
    }

    /**
     * The Builder for {@link OpenApiRequest}
     */
    public static class Builder {

        private String endpoint;

        private HttpMethod method;

        private Map<String, String> queryParameters;

        private Map<String, String> headers;

        Builder(String endpoint) {
            requireNonNull(endpoint, "The 'endpoint' argument must not be null");
            this.endpoint = endpoint;
        }

        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder queryParameter(OpenApiRequestParam param, String value) {
            return queryParameter(param.getName(), value);
        }

        public Builder queryParameter(OpenApiRequestParam param, Object value) {
            return queryParameter(param.getName(), param.toValue(value));
        }

        protected Builder queryParameter(String name, Object value) {
            return queryParameter(name, value == null ? null : value.toString());
        }

        protected Builder queryParameter(String name, String value) {
            return set(name, value, this::getQueryParameters);
        }

        public Builder header(OpenApiRequestHeader header, String value) {
            return header(header.getName(), value);
        }

        public Builder header(OpenApiRequestHeader header, Object value) {
            return header(header.getName(), header.toValue(value));
        }

        protected Builder header(String name, Object value) {
            return header(name, value == null ? null : value.toString());
        }

        protected Builder header(String name, String value) {
            return set(name, value, this::getHeaders);
        }

        private Builder set(String name, String value, Supplier<Map<String, String>> mapSupplier) {
            if (name == null && value == null) {
                return this;
            }
            Map<String, String> map = mapSupplier.get();
            map.put(name, value);
            return this;
        }

        private Map<String, String> getQueryParameters() {
            Map<String, String> params = this.queryParameters;
            if (params == null) {
                params = new HashMap<>();
                this.queryParameters = params;
            }
            return params;
        }

        private Map<String, String> getHeaders() {
            Map<String, String> headers = this.headers;
            if (headers == null) {
                headers = new HashMap<>();
                this.headers = headers;
            }
            return headers;
        }

        public OpenApiRequest build() {
            return new OpenApiRequest(this.endpoint, this.method, this.queryParameters, this.headers);
        }

        public static Builder from(OpenApiRequest request) {
            Builder builder = create(request.endpoint);
            builder.method = request.method;
            builder.queryParameters = request.queryParameters;
            return builder;
        }

        public static Builder create(String endpoint) {
            return new Builder(endpoint);
        }
    }
}
