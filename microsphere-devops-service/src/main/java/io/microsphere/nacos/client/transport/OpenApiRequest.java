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

import java.util.Map;

import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static java.util.Collections.emptyMap;

/**
 * The Nacos request interface for Open API
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiClient
 * @see OpenApiResponse
 * @since 1.0.0
 */
public interface OpenApiRequest {

    /**
     * Returns the part of this request's URL from the protocol name up to the query string
     * in the first line of the HTTP request.
     *
     * @return non-null
     */
    String getEndpoint();

    /**
     * Get the HTTP method
     *
     * @return non-null
     */
    default HttpMethod getMethod() {
        return GET;
    }

    /**
     * Get the parameters from the query string
     *
     * @return non-null
     */
    default Map<String, String> getQueryParameters() {
        return emptyMap();
    }
}
