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

import io.microsphere.nacos.client.v1.namespace.model.Namespace;

import java.util.List;

/**
 * The Client for Nacos Namespace
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Namespace
 * @since 1.0.0
 */
public interface NamespaceClient {

    /**
     * Get the list of all Namespaces
     *
     * @return non-null
     */
    List<Namespace> getAllNamespaces();

    /**
     * Create a new {@link Namespace}
     *
     * @param namespaceId   The id of {@link Namespace} (required)
     * @param namespaceName the name of {@link Namespace} (required)
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    default boolean createNamespace(String namespaceId, String namespaceName) {
        return createNamespace(namespaceId, namespaceName, null);
    }

    /**
     * Create a new {@link Namespace}
     *
     * @param namespaceId   The id of {@link Namespace} (required)
     * @param namespaceName the name of {@link Namespace} (required)
     * @param namespaceDesc the description of {@link Namespace} (optional)
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    boolean createNamespace(String namespaceId, String namespaceName, String namespaceDesc);

    /**
     * Update the {@link Namespace} by id
     *
     * @param namespaceId   The id of {@link Namespace} (required)
     * @param namespaceName the name of {@link Namespace} (required)
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    default boolean updateNamespace(String namespaceId, String namespaceName) {
        return createNamespace(namespaceId, namespaceName, null);
    }

    /**
     * Update the {@link Namespace} by id
     *
     * @param namespaceId   The id of {@link Namespace} (required)
     * @param namespaceName the name of {@link Namespace} (required)
     * @param namespaceDesc the description of {@link Namespace} (optional)
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    boolean updateNamespace(String namespaceId, String namespaceName, String namespaceDesc);
}
