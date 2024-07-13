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

import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.v1.namespace.model.Namespace;
import io.microsphere.nacos.client.v1.naming.model.Service;

/**
 * The Nacos Client for Service
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Page
 * @see Namespace
 * @since 1.0.0
 */
public interface ServiceClient {

    /**
     * Default page number : 1
     */
    int DEFAULT_PAGE_NUMBER = 1;

    /**
     * Default page size : 10
     */
    int DEFAULT_PAGE_SIZE = 10;

    /**
     * Get the pagination of service names by the specified namespaceId
     *
     * @param namespaceId the id of {@link Namespace}
     * @return non-null {@link Page}
     */
    default Page<String> getServiceNames(String namespaceId) {
        return getServiceNames(namespaceId, DEFAULT_PAGE_NUMBER);
    }

    /**
     * Get the pagination of service names by the specified namespaceId
     *
     * @param namespaceId the id of {@link Namespace}
     * @param pageNumber  the number of page, starts with 1
     * @return non-null {@link Page}
     */
    default Page<String> getServiceNames(String namespaceId, int pageNumber) {
        return getServiceNames(namespaceId, pageNumber, DEFAULT_PAGE_SIZE);
    }

    /**
     * Get the pagination of service names by the specified namespaceId
     *
     * @param namespaceId the id of {@link Namespace}
     * @param pageNumber  the number of page, starts with 1
     * @param pageSize    the expected size of one page
     * @return non-null {@link Page}
     */
    default Page<String> getServiceNames(String namespaceId, int pageNumber, int pageSize) {
        return getServiceNames(namespaceId, null, pageNumber, pageSize);
    }

    /**
     * Get the pagination of service names by the specified namespaceId and groupName
     *
     * @param namespaceId the id of {@link Namespace}
     * @param groupName   the name of group (optional)
     * @param pageNumber  the number of page, starts with 1
     * @param pageSize    the expected size of one page
     * @return non-null {@link Page}
     */
    Page<String> getServiceNames(String namespaceId, String groupName, int pageNumber, int pageSize);

    /**
     * Get an instance of {@link Service} by the specified namespaceId and serviceName
     *
     * @param namespaceId the id of {@link Namespace}
     * @param serviceName the name of service
     * @return an instance of {@link Service} if found, otherwise <code>null</code>
     */
    default Service getService(String namespaceId, String serviceName) {
        return getService(namespaceId, null, serviceName);
    }

    /**
     * Get an instance of {@link Service} by the specified namespaceId, groupName and serviceName
     *
     * @param namespaceId the id of {@link Namespace}
     * @param groupName   the name of group (optional)
     * @param serviceName the name of service
     * @return an instance of {@link Service} if found, otherwise <code>null</code>
     */
    Service getService(String namespaceId, String groupName, String serviceName);

    /**
     * Create a new {@link Service}
     *
     * @param service {@link Service}
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    boolean createService(Service service);

    /**
     * Update the specified {@link Service}
     *
     * @param service {@link Service}
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    boolean updateService(Service service);
}