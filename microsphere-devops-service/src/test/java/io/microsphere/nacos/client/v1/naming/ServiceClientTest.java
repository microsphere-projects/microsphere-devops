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
import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.v1.naming.model.Selector;
import io.microsphere.nacos.client.v1.naming.model.Service;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.microsphere.nacos.client.constants.Constants.PAGE_NUMBER;
import static io.microsphere.nacos.client.constants.Constants.PAGE_SIZE;
import static java.util.Collections.singleton;
import static java.util.Collections.singletonMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link ServiceClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ServiceClient
 * @see OpenApiServiceClient
 * @since 1.0.0
 */
public class ServiceClientTest extends OpenApiTest {

    static final String TEST_SERVICE_NAME = "test-service";

    static final String TEST_CLUSTER = "DEFAULT";

    static final Set<String> TEST_CLUSTERS = singleton(TEST_CLUSTER);

    @Test
    public void test() {
        // Test getServiceNames()
        ServiceClient client = new OpenApiServiceClient(openApiClient);
        Page<String> page = client.getServiceNames(TEST_NAMESPACE_ID);
        List<String> serviceNames = page.getElements();

        assertEquals(PAGE_NUMBER, page.getPageNumber());
        assertEquals(PAGE_SIZE, page.getPageSize());
        assertTrue(page.getTotalElements() < PAGE_SIZE);

        // Test getService()
        for (String serviceName : serviceNames) {
            Service service = client.getService(TEST_NAMESPACE_ID, serviceName);
            assertEquals(serviceName, service.getName());
            assertEquals(TEST_NAMESPACE_ID, service.getNamespaceId());
        }


        // Test createService()
        // Bug? failed response from Nacos Server
        Service service = createService();
        assertTrue(client.createService(service));


        // Test getService()
        Service testService = client.getService(TEST_NAMESPACE_ID, TEST_SERVICE_NAME);
        assertEquals(TEST_NAMESPACE_ID, testService.getNamespaceId());
        assertEquals(TEST_GROUP_NAME, testService.getGroupName());
        assertEquals(TEST_SERVICE_NAME, testService.getName());
        assertEquals(0.0f, testService.getProtectThreshold());
        assertEquals("none", testService.getSelector().getType());


        // Test UpdateService();
        service.setProtectThreshold(0.5f);
        assertTrue(client.updateService(service));


        // Test DeleteService()
        assertTrue(client.deleteService(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_SERVICE_NAME));

        // Test getService() : not found
        testService = client.getService(TEST_NAMESPACE_ID, TEST_SERVICE_NAME);
        assertNull(testService);
    }

    private Service createService() {
        Service service = new Service();
        service.setNamespaceId(TEST_NAMESPACE_ID);
        service.setGroupName(TEST_GROUP_NAME);
        service.setName(TEST_SERVICE_NAME);
        service.setProtectThreshold(0.0f);
        Selector selector = new Selector();
        selector.setType("none");
        service.setSelector(selector);
        Map<String, String> metadata = singletonMap("servce.name", TEST_SERVICE_NAME);
        service.setMetadata(metadata);
        return service;
    }
}
