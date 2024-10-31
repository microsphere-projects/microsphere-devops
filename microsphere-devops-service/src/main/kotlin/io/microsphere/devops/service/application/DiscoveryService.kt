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
package io.microsphere.devops.service.application

import io.microsphere.devops.api.entity.Application
import io.microsphere.devops.api.entity.ApplicationInstance
import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import org.springframework.beans.factory.DisposableBean

/**
 * The Discovery Service Interface
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Cluster
 * @see Namespace
 * @see Application
 * @see ApplicationInstance
 * @since 1.0.0
 */
interface DiscoveryService : DisposableBean {

    /**
     * Get all instances of [Namespace] from the specified [Cluster]
     * @param cluster the specified [Cluster]
     */
    fun getNamespaces(cluster: Cluster): List<Namespace>;

    /**
     * Get all instances of [Application] from the specified [Namespace]
     * @param namespace the specified [Namespace]
     */
    fun getApplications(namespace: Namespace): List<Application>;

    /**
     * Get all instances of [ApplicationInstance] from the specified [Application]
     * @param application the specified [Application]
     */
    fun getApplicationInstances(application: Application): List<ApplicationInstance>;

    /**
     * The type of cluster
     */
    fun getClusterType(): Cluster.Type;

}