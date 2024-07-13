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

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.v1.server.model.ServerState;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * {@link OpenApiHttpClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiHttpClient
 * @since 1.0.0
 */
public class OpenApiHttpClientTest extends OpenApiTest {

    @Test
    public void testExecute() throws IOException {

        OpenApiRequest.Builder builder = OpenApiRequest.Builder.create("/v1/console/server/state");
        OpenApiRequest request = builder.build();

        OpenApiResponse response = openApiClient.execute(request);

        assertEquals(200, response.getStatusCode());
        assertEquals("", response.getStatusMessage());
        assertNotNull(response.getContent());
    }

    @Test
    public void testExecuteForPayload() throws IOException {
        OpenApiRequest.Builder builder = OpenApiRequest.Builder.create("/v1/console/server/state");
        OpenApiRequest request = builder.build();

        ServerState serverState = openApiClient.execute(request, ServerState.class);
        assertNotNull(serverState);
        assertNotNull(serverState.getMode());
        assertNotNull(serverState.getVersion());
        assertNull(serverState.getFunctionMode());
    }
}
