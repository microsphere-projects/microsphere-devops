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
import io.microsphere.nacos.client.v1.server.model.Switch;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiSwitchClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiSwitchClient
 * @since 1.0.0
 */
public class OpenApiSwitchClientTest extends OpenApiTest {

    @Test
    public void test() {
        OpenApiSwitchClient client = new OpenApiSwitchClient(this.openApiClient);

        // Test getSwitch()
        Switch sw = client.getSwitch();

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

        Switch.HealthParams httpHealthParams = sw.getHttpHealthParams();
        assertEquals(5000, httpHealthParams.getMax());
        assertEquals(500, httpHealthParams.getMin());
        assertEquals(0.85f, httpHealthParams.getFactor());

        Switch.HealthParams tcpHealthParams = sw.getTcpHealthParams();
        assertEquals(5000, tcpHealthParams.getMax());
        assertEquals(1000, tcpHealthParams.getMin());
        assertEquals(0.75f, tcpHealthParams.getFactor());

        Switch.HealthParams mysqlHealthParams = sw.getMysqlHealthParams();
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
        assertTrue(client.updateSwitch("pushCacheMillis", "20000"));
        sw = client.getSwitch();
        assertEquals(20000, sw.getDefaultPushCacheMillis());

    }
}
