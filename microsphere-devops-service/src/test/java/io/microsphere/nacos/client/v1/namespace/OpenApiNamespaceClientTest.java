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
package io.microsphere.nacos.client.v1.namespace;

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.v1.namespace.model.Namespace;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiNamespaceClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiNamespaceClient
 * @since 1.0.0
 */
public class OpenApiNamespaceClientTest extends OpenApiTest {

    private static final String NAMESPACE_ID = "test-001";

    private static final String NAMESPACE_NAME = "test-001";

    private static final String NAMESPACE_DESC = "test-001-desc";

    private static final String MODIFIED_NAMESPACE_NAME = "test-001(modified)";

    private static final String MODIFIED_NAMESPACE_DESC = "test-001 DESC ...";

    @Test
    public void test() {
        OpenApiNamespaceClient namespaceClient = new OpenApiNamespaceClient(this.openApiClient);

        // Test getAllNamespace()
        List<Namespace> namespaces = namespaceClient.getAllNamespaces();
        int namespacesSize = namespaces.size();
        assertNotNull(namespaces);
        assertTrue(namespacesSize > 0);
        Namespace namespace = namespaces.get(0);
        assertEquals("public", namespace.getNamespaceName());

        // Test getNamespace()
        namespace = namespaceClient.getNamespace(NAMESPACE_ID);

        // Not found
        assertNull(namespace);

        // Test createNamespace()
        assertTrue(namespaceClient.createNamespace(NAMESPACE_ID, NAMESPACE_NAME, NAMESPACE_DESC));
        namespaces = namespaceClient.getAllNamespaces();
        namespace = namespaces.get(namespaces.size() - 1);
        assertEquals(NAMESPACE_ID, namespace.getNamespaceId());
        assertEquals(NAMESPACE_NAME, namespace.getNamespaceName());

        // Test updateNamespace()
        for (int i = 0; i < 5; i++) {
            assertTrue(namespaceClient.updateNamespace(NAMESPACE_ID, MODIFIED_NAMESPACE_NAME, MODIFIED_NAMESPACE_DESC));
        }

        // Test deleteNamespace()
        assertTrue(namespaceClient.deleteNamespace(NAMESPACE_ID));
    }
}
