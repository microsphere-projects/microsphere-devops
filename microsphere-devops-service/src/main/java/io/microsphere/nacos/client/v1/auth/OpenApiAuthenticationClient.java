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
package io.microsphere.nacos.client.v1.auth;

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiClientException;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.auth.model.Authentication;

/**
 * The {@link AuthenticationClient} for <a href="https://nacos.io/docs/v1/auth/">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see AuthenticationClient
 * @since 1.0.0
 */
public class OpenApiAuthenticationClient implements AuthenticationClient {

    private final OpenApiClient openApiClient;

    private final NacosClientConfig nacosClientConfig;

    public OpenApiAuthenticationClient(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        this.openApiClient = openApiClient;
        this.nacosClientConfig = nacosClientConfig;
    }

    @Override
    public Authentication authenticate() {
        OpenApiRequest request = OpenApiRequest.Builder.create("/v1/auth/login")
                .queryParameter("username", nacosClientConfig.getUsername())
                .queryParameter("password", nacosClientConfig.getPassword())
                .build();
        Authentication authentication = null;
        try {
            authentication = openApiClient.execute(request, Authentication.class);
        } catch (OpenApiClientException e) {

        }
        return authentication;
    }
}
