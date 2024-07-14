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
package io.microsphere.nacos.client.v1.naming;

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.v1.naming.model.InstancesList;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiInstanceClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiInstanceClient
 * @since 1.0.0
 */
public class OpenApiInstanceClientTest extends OpenApiTest {

    private static final String TEST_NAMESPACE_ID = "test";

    private static final String TEST_GROUP_NAME = "test-group";

    private static final String TEST_SERVICE_NAME = "test-service";

    private static final Set<String> TEST_CLUSTERS = singleton("DEFAULT");

    @Test
    public void test() {
        OpenApiInstanceClient client = new OpenApiInstanceClient(this.openApiClient);

        // Test getInstancesList()
        InstancesList instancesList = client.getInstancesList(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_SERVICE_NAME);
        assertNull(instancesList.getDom());
        assertNull(instancesList.getCacheMillis());
        assertNull(instancesList.getUseSpecifiedURL());
        assertEquals("test-group@@test-service", instancesList.getName());
        assertNull(instancesList.getChecksum());
        assertNull(instancesList.getLastRefTime());
        assertNull(instancesList.getEnv());
        assertTrue(instancesList.getClusters().isEmpty());
        assertTrue(instancesList.getHosts().isEmpty());
        assertNull(instancesList.getMetadata());
    }
}
