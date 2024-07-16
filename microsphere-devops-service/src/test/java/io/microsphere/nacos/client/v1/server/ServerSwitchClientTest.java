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
import io.microsphere.nacos.client.v1.server.model.ServerSwitch;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link ServerSwitchClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ServerSwitchClient
 * @since 1.0.0
 */
public class ServerSwitchClientTest extends OpenApiTest {

    @Test
    public void test() {
        ServerSwitchClient client = new OpenApiServerClient(this.openApiClient);

        // Test getSwitch()
        ServerSwitch sw = client.getServerSwitch();

        assertNull(sw.getMasters());
        assertEquals(10000, sw.getDefaultPushCacheMillis());
        assertEquals(5000, sw.getClientBeatInterval());
        assertEquals(3000, sw.getDefaultCacheMillis());
        assertEquals(0.7f, sw.getDistroThreshold());

        assertTrue(sw.getAutoChangeHealthCheckEnabled());
        assertTrue(sw.getDistroEnabled());
        assertTrue(sw.getEnableStandalone());
        assertTrue(sw.getPushEnabled());
        assertEquals(3, sw.getCheckTimes());

        ServerSwitch.HealthParams httpHealthParams = sw.getHttpHealthParams();
        assertEquals(5000, httpHealthParams.getMax());
        assertEquals(500, httpHealthParams.getMin());
        assertEquals(0.85f, httpHealthParams.getFactor());

        ServerSwitch.HealthParams tcpHealthParams = sw.getTcpHealthParams();
        assertEquals(5000, tcpHealthParams.getMax());
        assertEquals(1000, tcpHealthParams.getMin());
        assertEquals(0.75f, tcpHealthParams.getFactor());

        ServerSwitch.HealthParams mysqlHealthParams = sw.getMysqlHealthParams();
        assertEquals(3000, mysqlHealthParams.getMax());
        assertEquals(2000, mysqlHealthParams.getMin());
        assertEquals(0.65f, mysqlHealthParams.getFactor());

        List<String> incrementalList = sw.getIncrementalList();
        assertTrue(incrementalList.isEmpty());

        assertEquals(2000, sw.getServerStatusSynchronizationPeriodMillis());
        assertEquals(5000, sw.getServiceStatusSynchronizationPeriodMillis());

        assertFalse(sw.getDisableAddIP());
        assertFalse(sw.getSendBeatOnly());
        assertTrue(sw.getLightBeatEnabled());

        // Test updateSwitch()
        // see com.alibaba.nacos.naming.misc.SwitchEntry
        // you will crazy :D
        assertTrue(client.updateServerSwitch("pushCacheMillis", "20000"));
        sw = client.getServerSwitch();
        assertEquals(20000, sw.getDefaultPushCacheMillis());

        assertTrue(client.updateServerSwitch("pushCacheMillis", "10000"));
        sw = client.getServerSwitch();
        assertEquals(10000, sw.getDefaultPushCacheMillis());

    }
}
