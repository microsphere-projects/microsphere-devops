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

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.v1.server.model.Server;
import io.microsphere.nacos.client.v1.server.model.ServersList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * {@link ServersListClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ServerSwitchClient
 * @since 1.0.0
 */
public class ServersListClientTest extends OpenApiTest {

    @Test
    public void test() {
        ServersListClient client = new OpenApiServerClient(this.openApiClient);

        // Test getServersList()
        ServersList serversList = client.getServersList();
        List<Server> servers = serversList.getServers();

        assertNotNull(serversList);
        assertNotNull(servers);
        assertFalse(serversList.getServers().isEmpty());

        Server server = servers.get(0);
        assertNotNull(server.getIp());
        assertNotNull(server.getPort());
        assertNotNull(server.getSite());
        assertNotNull(server.getWeight());
        assertNotNull(server.getAdWeight());
        assertNotNull(server.getAlive());
        assertNotNull(server.getLastRefTime());
        assertNull(server.getLastRefTimeStr());
        assertNotNull(server.getKey());

    }
}
