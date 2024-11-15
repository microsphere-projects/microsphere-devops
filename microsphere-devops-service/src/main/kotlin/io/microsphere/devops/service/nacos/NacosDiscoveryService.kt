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
import com.fasterxml.jackson.databind.ObjectMapper
import io.microsphere.devops.api.entity.Application
import io.microsphere.devops.api.entity.ApplicationInstance
import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.service.application.DiscoveryService
import io.microsphere.nacos.client.NacosClientConfig
import io.microsphere.nacos.client.common.OpenApiTemplateClient
import io.microsphere.nacos.client.common.discovery.model.Instance
import io.microsphere.nacos.client.constants.Constants.DEFAULT_CLUSTER_NAME
import io.microsphere.nacos.client.constants.Constants.DEFAULT_GROUP_NAME
import io.microsphere.nacos.client.constants.Constants.DEFAULT_HEALTHY_ONLY
import io.microsphere.nacos.client.v2.NacosClientV2
import io.microsphere.nacos.client.v2.OpenApiNacosClientV2
import org.springframework.beans.factory.annotation.Value
import org.springframework.util.StringUtils.hasText
import java.util.concurrent.ConcurrentHashMap

/**
 * [NacosClientV2] [DiscoveryService]
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see NacosClientV2
 * @see DiscoveryService
 * @since 1.0.0
 */
class NacosDiscoveryService(
    @Value("\${spring.application.name:default}")
    val currentApplicationName: String,
) : DiscoveryService {

    private val nacosClientsCache = ConcurrentHashMap<String, NacosClientV2>();

    override fun getNamespaces(cluster: Cluster): List<Namespace> {
        val client = getNacosClient(cluster);
        return client.allNamespaces.map { ns -> createNamespace(ns, cluster) };
    }

    override fun getApplications(namespace: Namespace): List<Application> {
        val client = getNacosClient(namespace);

        val namespaceId = namespace.name;
        var pageNumber = 0;
        val pageSize = 100;
        var page = client.getServiceNames(namespaceId, pageNumber, pageSize);

        val serviceNames = ArrayList<String>(page.totalElements);

        serviceNames.addAll(page.elements);
        while (page.hasNext()) {
            pageNumber += pageSize;
            page = client.getServiceNames(namespaceId, pageNumber, pageSize);
            serviceNames.addAll(page.elements);
        }

        return serviceNames.map { serviceName -> createApplication(serviceName, namespace) }
    }

    override fun getApplicationInstances(application: Application): List<ApplicationInstance> {
        val client = getNacosClient(application);
        val namespaceId = application.namespace!!.name;
        val serviceName = application.name;
        val groupName = DEFAULT_GROUP_NAME;
        val clusterName = DEFAULT_CLUSTER_NAME;
        val instancesList = client.getInstancesList(
            namespaceId,
            groupName,
            clusterName,
            serviceName,
            DEFAULT_HEALTHY_ONLY,
            currentApplicationName
        );
        return instancesList.hosts.map { instance -> createApplicationInstance(instance, application) }
    }

    override fun getClusterType() = Cluster.Type.NACOS;

    private fun createApplication(
        serviceName: String,
        namespace: Namespace
    ): Application {
        val application = Application(serviceName);
        application.namespace = namespace;
        return application;
    }


    private fun createApplicationInstance(
        instance: Instance,
        application: Application
    ): ApplicationInstance {
        var applicationInstance = ApplicationInstance(instance.instanceId, instance.ip, instance.port);
        val metadata = instance.metadata;
        if (!metadata.isEmpty()) {
            val objectMapper = ObjectMapper();
            applicationInstance.metadata = objectMapper.writeValueAsString(metadata);
        }

        if (!instance.enabled) {
            applicationInstance.status = ApplicationInstance.Status.DOWN;
        }

        applicationInstance.application = application;

        return applicationInstance;
    }

    private fun createNamespace(
        ns: io.microsphere.nacos.client.common.namespace.model.Namespace,
        cluster: Cluster
    ): Namespace {
        val namespaceId = resolveNamespaceId(ns.namespaceId);
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

    private fun getNacosClient(application: Application): NacosClientV2 {
        return getNacosClient(application.namespace!!);
    }

    private fun getNacosClient(namespace: Namespace): NacosClientV2 {
        return getNacosClient(namespace.cluster!!);
    }

    private fun getNacosClient(cluster: Cluster): NacosClientV2 {
        return nacosClientsCache.computeIfAbsent(cluster.url) { url ->
            createNacosClient(cluster)
        };
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