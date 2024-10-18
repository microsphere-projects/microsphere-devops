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
package io.microsphere.devops.service.nacos

import com.alibaba.nacos.api.common.Constants.DEFAULT_NAMESPACE_ID
import io.microsphere.devops.api.entity.Application
import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.service.application.ApplicationServiceFacade
import io.microsphere.nacos.client.NacosClientConfig
import io.microsphere.nacos.client.common.OpenApiTemplateClient
import io.microsphere.nacos.client.v2.NacosClientV2
import io.microsphere.nacos.client.v2.OpenApiNacosClientV2
import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import org.springframework.util.StringUtils.hasText
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**
 * Nacos Service
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Service
 * @since 1.0.0
 */
@Service
class NacosService(
    val applicationServiceFacade: ApplicationServiceFacade
) : DisposableBean {
    private val nacosClientsCache: ConcurrentMap<String, NacosClientV2> = ConcurrentHashMap();

    @Transactional
    fun initClusters(clusters: List<Cluster>) {
        for (cluster in clusters) {
            initCluster(cluster);
        }
    }

    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun initCluster(cluster: Cluster) {
        val client = nacosClientsCache.computeIfAbsent(cluster.url) { url ->
            createNacosClient(cluster)
        };
        initNamespaces(cluster, client);
    }

    internal fun initNamespaces(cluster: Cluster, client: NacosClientV2) {
        var namespaceService = applicationServiceFacade.namespaceService;

        val updatedNamespacesMap: MutableMap<String, Namespace> = mutableMapOf();

        val allNamespaces = ArrayList<Namespace>();

        for (namespace in namespaceService.findAllByClusterIdAndStatus(cluster.id!!)) {
            updatedNamespacesMap[namespace.name] = namespace;
        }

        for (ns in client.allNamespaces) {
            val namespaceId = resolveNamespaceId(ns.namespaceId);
            var namespace = updatedNamespacesMap.remove(namespaceId);
            if (namespace == null) {
                namespace = createNamespace(namespaceId, ns, cluster);
            }
            allNamespaces.add(namespace);
        }

        for (namespace in updatedNamespacesMap.values) {
            namespace.status = Namespace.Status.UNKNOWN;
            allNamespaces.add(namespace);
        }

        for (namespace in allNamespaces) {
            namespaceService.saveOrUpdateNamespace(namespace);
            when (namespace.status) {
                Namespace.Status.ACTIVE -> initApplication(namespace, client)
                else -> continue;
            }
        }
    }

    internal fun initApplication(namespace: Namespace, client: NacosClientV2) {
        var applicationService = applicationServiceFacade.applicationService;

        val removedApplicationsMap: MutableMap<String, Application> = mutableMapOf();

        for (application in applicationService.findAllByNamespaceId(namespace.id!!)) {
            removedApplicationsMap[application.name] = application;
        }

        val serviceNames = ArrayList<String>();
        val namespaceId = namespace.name;
        var pageNumber = 0;
        val pageSize = 100;
        var page = client.getServiceNames(namespaceId, pageNumber, pageSize);

        serviceNames.addAll(page.elements);
        while (page.hasNext()) {
            pageNumber += pageSize;
            page = client.getServiceNames(namespaceId, pageNumber, pageSize);
            serviceNames.addAll(page.elements);
        }

        // Remove the unknown services
        serviceNames.forEach { name -> removedApplicationsMap.remove(name) }

        applicationService.deleteAll(removedApplicationsMap.values);

        for (serviceName in serviceNames) {
            var application = Application(serviceName);
            application.namespace = namespace;
            applicationService.saveOrUpdateApplication(application);
        }
    }

    private fun createNamespace(
        namespaceId: String,
        ns: io.microsphere.nacos.client.common.namespace.model.Namespace,
        cluster: Cluster
    ): Namespace {
        val namespace = Namespace(namespaceId, Namespace.Status.ACTIVE);
        namespace.description = ns.namespaceDesc;
        namespace.cluster = cluster;
        return namespace;
    }

    private fun resolveNamespaceId(namespaceId: String?): String {
        if (hasText(namespaceId)) {
            return namespaceId!!;
        }
        return DEFAULT_NAMESPACE_ID;
    }

    private fun createNacosClient(cluster: Cluster): NacosClientV2 {
        var config = NacosClientConfig();
        config.serverAddress = cluster.url;
        if (hasText(cluster.username)) {
            config.userName = cluster.username;
        }
        if (hasText(cluster.password)) {
            config.password = cluster.password;
        }
        return OpenApiNacosClientV2(config);
    }

    override fun destroy() {
        for (client in nacosClientsCache.values) {
            if (client is OpenApiTemplateClient) {
                client.openApiClient.close();
            }
        }
        nacosClientsCache.clear();
    }
}