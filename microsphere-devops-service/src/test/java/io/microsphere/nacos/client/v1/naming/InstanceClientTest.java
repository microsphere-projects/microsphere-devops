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

import io.microsphere.nacos.client.ErrorCode;
import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.transport.OpenApiClientException;
import io.microsphere.nacos.client.v1.naming.model.BatchUpdateMetadataResult;
import io.microsphere.nacos.client.v1.naming.model.DeleteInstance;
import io.microsphere.nacos.client.v1.naming.model.Instance;
import io.microsphere.nacos.client.v1.naming.model.InstancesList;
import io.microsphere.nacos.client.v1.naming.model.NewInstance;
import io.microsphere.nacos.client.v1.naming.model.QueryInstance;
import io.microsphere.nacos.client.v1.naming.model.UpdateHealthInstance;
import io.microsphere.nacos.client.v1.naming.model.UpdateInstance;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link InstanceClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see InstanceClient
 * @see OpenApiInstanceClient
 * @since 1.0.0
 */
public class InstanceClientTest extends OpenApiTest {

    private static final String TEST_NAMESPACE_ID = "test";

    private static final String TEST_GROUP_NAME = "test-group";

    private static final String TEST_SERVICE_NAME = "test-service";

    private static final String TEST_CLUSTER = "DEFAULT";

    private static final Set<String> TEST_CLUSTERS = singleton(TEST_CLUSTER);

    @Test
    public void test() {
        OpenApiInstanceClient client = new OpenApiInstanceClient(this.openApiClient);

        // Test register()
        Instance instance = createInstance();
        NewInstance newInstance = new NewInstance().from(instance);
        assertTrue(client.register(newInstance));
        assertEquals(TEST_NAMESPACE_ID, instance.getNamespaceId());
        assertEquals(TEST_GROUP_NAME, instance.getGroupName());
        assertEquals(TEST_SERVICE_NAME, instance.getServiceName());
        assertEquals(TEST_CLUSTER, instance.getClusterName());

        // Test getInstance()
        QueryInstance queryInstance = new QueryInstance().from(instance);
        Instance instance1 = client.getInstance(queryInstance);
        assertEquals(instance.getIp(), instance1.getIp());
        assertEquals(instance.getPort(), instance1.getPort());
        assertEquals(instance.getServiceName(), instance1.getServiceName());
        assertEquals(instance.getClusterName(), instance1.getClusterName());
        assertEquals(instance.getHealthy(), instance1.getHealthy());
        assertEquals(instance.getWeight(), instance1.getWeight());

        // Test refresh()
        instance.setWeight(50.0);
        UpdateInstance updateInstance = new UpdateInstance().from(instance);
        client.refresh(updateInstance);
        instance1 = client.getInstance(queryInstance);
        assertEquals(instance.getIp(), instance1.getIp());
        assertEquals(instance.getPort(), instance1.getPort());
        assertEquals(instance.getServiceName(), instance1.getServiceName());
        assertEquals(instance.getClusterName(), instance1.getClusterName());
        assertEquals(instance.getHealthy(), instance1.getHealthy());
        assertEquals(instance.getWeight(), instance1.getWeight());

        // Test updateHealth()
        OpenApiClientException exception = null;
        try {
            UpdateHealthInstance updateHealthInstance = new UpdateHealthInstance(true).from(instance);
            client.updateHealth(updateHealthInstance);
        } catch (OpenApiClientException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals(ErrorCode.BAD_REQUEST, exception.getErrorCode());

        // Test getInstancesList()
        InstancesList instancesList = client.getInstancesList(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_SERVICE_NAME);
        assertEquals(TEST_SERVICE_NAME, instancesList.getDom());
        assertEquals(3000, instancesList.getCacheMillis());
        assertFalse(instancesList.getUseSpecifiedURL());
        assertEquals("test-group@@test-service", instancesList.getName());
        assertNotNull(instancesList.getChecksum());
        assertNotNull(instancesList.getLastRefTime());
        assertNotNull(instancesList.getEnv());
        assertTrue(instancesList.getClusters().isEmpty());
        assertFalse(instancesList.getHosts().isEmpty());
        assertTrue(instancesList.getMetadata().isEmpty());

        // Test batchUpdateMetadata()
        instance1.setNamespaceId(TEST_NAMESPACE_ID);
        BatchUpdateMetadataResult result = client.batchUpdateMetadata(Arrays.asList(instance1), singletonMap("test-key", "test-value"));
        assertFalse(result.getUpdated().isEmpty());

        // Test deregister()
        DeleteInstance deleteInstance = new DeleteInstance().from(instance);
        assertTrue(client.deregister(deleteInstance));
    }

    private Instance createInstance() {
        Instance instance = new Instance();
        instance.setNamespaceId(TEST_NAMESPACE_ID);
        instance.setGroupName(TEST_GROUP_NAME);
        instance.setServiceName(TEST_SERVICE_NAME);
        instance.setClusterName(TEST_CLUSTER);
        instance.setIp("127.0.0.1");
        instance.setPort(8080);
        instance.setWeight(100.0);
        instance.setEnabled(true);
        instance.setHealthy(true);
        instance.setEphemeral(true);
        instance.setMetadata(singletonMap("test-key", "test-value"));
        return instance;
    }
}
