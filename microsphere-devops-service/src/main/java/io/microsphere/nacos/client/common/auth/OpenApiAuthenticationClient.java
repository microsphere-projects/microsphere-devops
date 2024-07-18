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
package io.microsphere.nacos.client.common.auth;

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.common.auth.model.Authentication;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;

import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.PASSWORD;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.USER_NAME;

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
                .method(POST)
                .queryParameter(USER_NAME, nacosClientConfig.getUsername())
                .queryParameter(PASSWORD, nacosClientConfig.getPassword())
                .build();

        return openApiClient.execute(request, Authentication.class);
    }
}
