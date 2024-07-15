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
package io.microsphere.nacos.client.v1.server;

import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.server.model.Switch;

import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.PUT;
import static io.microsphere.nacos.client.util.OpenApiUtils.isOkResponse;

/**
 * The {@link SwitchClient} for Open API
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see SwitchClient
 * @since 1.0.0
 */
public class OpenApiSwitchClient implements SwitchClient {

    private static final String ENDPOINT = "/v1/ns/operator/switches";

    private final OpenApiClient openApiClient;

    public OpenApiSwitchClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    @Override
    public Switch getSwitch() {
        OpenApiRequest request = OpenApiRequest.Builder.create(ENDPOINT)
                .method(GET)
                .build();
        return openApiClient.execute(request, Switch.class);
    }

    @Override
    public boolean updateSwitch(String switchName, String switchValue, boolean debug) {
        OpenApiRequest request = OpenApiRequest.Builder.create(ENDPOINT)
                .method(PUT)
                .queryParameter("entry", switchName)
                .queryParameter("value", switchValue)
                .queryParameter("debug", debug)
                .build();
        return isOkResponse(openApiClient, request);
    }

}
