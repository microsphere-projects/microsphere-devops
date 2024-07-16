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
     * Create a new {@link Service}
     *
     * @param service {@link Service}
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    boolean createService(Service service);

    /**
     * Delete the {@link Service} by the specified namespaceId and serviceName
     *
     * @param namespaceId the id of {@link Namespace}
     * @param serviceName the name of service
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    default boolean deleteService(String namespaceId, String serviceName) {
        return deleteService(namespaceId, null, serviceName);
    }

    /**
     * Delete the {@link Service} by the specified namespaceId, groupName and serviceName
     *
     * @param namespaceId the id of {@link Namespace}
     * @param groupName   the name of group (optional)
     * @param serviceName the name of service
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    boolean deleteService(String namespaceId, String groupName, String serviceName);

    /**
     * Update the specified {@link Service} with parameters :
     <table>
     <thead>
     <tr>
     <th>Name</th>
     <th>Type</th>
     <th>Required</th>
     <th>Description</th>
     </tr>
     </thead>
     <tbody>
     <tr>
     <td>serviceName</td>
     <td>String</td>
     <td>yes</td>
     <td>service name</td>
     </tr>
     <tr>
     <td>groupName</td>
     <td>String</td>
     <td>no</td>
     <td>group name</td>
     </tr>
     <tr>
     <td>namespaceId</td>
     <td>String</td>
     <td>no</td>
     <td>namespace id</td>
     </tr>
     <tr>
     <td>protectThreshold</td>
     <td>float</td>
     <td>no</td>
     <td>set value from 0 to 1, default 0</td>
     </tr>
     <tr>
     <td>metadata</td>
     <td>String</td>
     <td>no</td>
     <td>metadata of service</td>
     </tr>
     <tr>
     <td>selector</td>
     <td>JSON</td>
     <td>no</td>
     <td>visit strategy</td>
     </tr>
     </tbody>
     </table>
     *
     * @param service {@link Service}
     * @return <code>true</code> if successful, otherwise <code>false</code>
     */
    boolean updateService(Service service);

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

}
