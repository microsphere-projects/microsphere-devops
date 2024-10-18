package io.microsphere.devops.service.application

import com.fasterxml.jackson.databind.ObjectMapper
import io.microsphere.devops.api.entity.Application
import io.microsphere.devops.api.entity.ApplicationInstance
import io.microsphere.devops.api.entity.ApplicationInstance.Status
import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import org.springframework.cloud.client.ServiceInstance
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URI

@Service
class ApplicationServiceFacade(
    val clusterService: ClusterService,
    val namespaceService: NamespaceService,
    val applicationService: ApplicationService,
    val applicationInstanceService: ApplicationInstanceService
) {

    @Transactional
    fun saveOrUpdate(cluster: Cluster, namespace: Namespace, serviceInstance: ServiceInstance) {
        // Persist Cluster
        namespace.cluster = clusterService.saveOrUpdateCluster(cluster);
        // Persist Namespace
        namespaceService.saveOrUpdateNamespace(namespace);

        // Application
        val applicationName = serviceInstance.serviceId;
        val application = applicationService.saveOrUpdateApplication(Application(applicationName, namespace));

        //  ApplicationInstance
        val objectMapper = ObjectMapper();

        val instanceId = serviceInstance.instanceId ?: buildInstanceId(serviceInstance);
        val host = serviceInstance.host;
        val port = serviceInstance.port;
        val uri = serviceInstance.uri ?: URI("${host}:${port}");
        val metadata = objectMapper.writeValueAsString(serviceInstance.metadata);
        val applicationInstance = ApplicationInstance(instanceId, host, port, uri, metadata, Status.UP, application);
        applicationInstanceService.saveOrUpdateApplicationInstance(applicationInstance);
    }

    private fun buildInstanceId(serviceInstance: ServiceInstance): String {
        return "${serviceInstance.serviceId}-${serviceInstance.host}:${serviceInstance.port}";
    }
}