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

import static java.util.Collections.unmodifiableMap;

/**
 * The Nacos request for Open API
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiClient
 * @see OpenApiResponse
 * @since 1.0.0
 */
public class OpenApiRequest {

    private final String endpoint;

    private final HttpMethod method;

    private final Map<String, String> queryParameters;

    public OpenApiRequest(String endpoint, HttpMethod method, Map<String, String> queryParameters) {
        this.endpoint = endpoint;
        this.method = method;
        this.queryParameters = unmodifiableMap(queryParameters);
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
        return queryParameters;
    }

    /**
     * The Builder for {@link OpenApiRequest}
     */
    public static class Builder {

        private String endpoint;

        private HttpMethod method = HttpMethod.GET;

        private Map<String, String> queryParameters = new HashMap<>();

        Builder(String endpoint) {
            this.endpoint = endpoint;
        }

        public Builder method(HttpMethod method) {
            this.method = method;
            return this;
        }

        public Builder queryParameter(String name, String value) {
            this.queryParameters.put(name, value);
            return this;
        }

        public OpenApiRequest build() {
            return new OpenApiRequest(this.endpoint, this.method, this.queryParameters);
        }

        public static Builder create(String endpoint) {
            return new Builder(endpoint);
        }
    }
}
