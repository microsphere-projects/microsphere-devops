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
import io.microsphere.nacos.client.v1.server.model.ServerMetrics;
import io.microsphere.nacos.client.v1.server.model.ServerState;
import io.microsphere.nacos.client.v1.server.model.ServerSwitch;
import io.microsphere.nacos.client.v1.server.model.ServersList;

import static io.microsphere.nacos.client.http.HttpMethod.PUT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVER_SWITCH_DEBUG;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVER_SWITCH_ENTRY;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVER_SWITCH_VALUE;
import static io.microsphere.nacos.client.util.OpenApiUtils.isOkResponse;

/**
 * The {@link ServerClient} for Open API
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ServerClient
 * @since 1.0.0
 */
public class OpenApiServerClient implements ServerClient {

    public static final String SERVER_SWITCH_ENDPOINT = "/v1/ns/operator/switches";

    public static final String SERVER_METRICS_ENDPOINT = "/v1/ns/operator/metrics";

    public static final String SERVERS_LIST_ENDPOINT = "/v1/ns/operator/servers";

    private final OpenApiClient openApiClient;

    public OpenApiServerClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    @Override
    public ServerState getServerState() {
        OpenApiRequest request = OpenApiRequest.Builder.create("/v1/console/server/state")
                .build();
        return this.openApiClient.execute(request, ServerState.class);
    }

    @Override
    public ServerSwitch getServerSwitch() {
        OpenApiRequest request = OpenApiRequest.Builder.create(SERVER_SWITCH_ENDPOINT)
                .build();
        return this.openApiClient.execute(request, ServerSwitch.class);
    }

    @Override
    public boolean updateServerSwitch(String switchName, String switchValue, boolean debug) {
        OpenApiRequest request = OpenApiRequest.Builder.create(SERVER_SWITCH_ENDPOINT)
                .method(PUT)
                .queryParameter(SERVER_SWITCH_ENTRY, switchName)
                .queryParameter(SERVER_SWITCH_VALUE, switchValue)
                .queryParameter(SERVER_SWITCH_DEBUG, debug)
                .build();
        return isOkResponse(this.openApiClient, request);
    }

    @Override
    public ServerMetrics getServerMetrics() {
        OpenApiRequest request = OpenApiRequest.Builder.create(SERVER_METRICS_ENDPOINT)
                .build();
        return this.openApiClient.execute(request, ServerMetrics.class);
    }

    @Override
    public ServersList getServersList() {
        OpenApiRequest request = OpenApiRequest.Builder.create(SERVERS_LIST_ENDPOINT)
                .build();
        return this.openApiClient.execute(request, ServersList.class);
    }
}
