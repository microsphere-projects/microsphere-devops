package io.microsphere.devops.service.application

import com.fasterxml.jackson.databind.ObjectMapper
import io.microsphere.devops.api.entity.Application
import io.microsphere.devops.api.entity.ApplicationInstance
import io.microsphere.devops.api.entity.ApplicationInstance.Status
import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import io.microsphere.jpa.event.EntityType
import io.microsphere.logging.LoggerFactory
import io.microsphere.spring.data.jpa.annotation.EntityListener
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cloud.client.ServiceInstance
import org.springframework.context.event.EventListener
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URI

@Service
class ApplicationServiceFacade(
    val clusterService: ClusterService,
    val namespaceService: NamespaceService,
    val applicationService: ApplicationService,
    val applicationInstanceService: ApplicationInstanceService,
    val discoveryServices: Array<DiscoveryService>,
    @Qualifier(DEFAULT_TASK_EXECUTOR_BEAN_NAME)
    val asyncTaskExecutor: AsyncTaskExecutor
) : InitializingBean {

    private val logger = LoggerFactory.getLogger(this::class.qualifiedName);

    private val discoveryServicesCache = HashMap<Cluster.Type, DiscoveryService>(Cluster.Type.values().size + 1);

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

    fun refreshClusters() {
        val clusters = clusterService.findAll();
        refreshClusters(clusters);
    }

    fun refreshClusters(clusters: List<Cluster>) {
        clusters.forEach { this::refreshCluster }
    }

    @Transactional
    @EntityListener(type = [EntityType.POST_PERSIST])
    fun refreshCluster(cluster: Cluster) {
        refreshNamespaces(cluster);
    }

    fun refreshNamespaces(cluster: Cluster) {
        val discoveryService = getDiscoveryService(cluster);

        // Load all namespaces of the specified cluster from the target infrastructure
        val newNamespaces = discoveryService.getNamespaces(cluster);

        if (newNamespaces.isEmpty()) {
            logger.warn("No Namespace was found in the cluster[url : {}]!", cluster.url);
            return;
        }

        val existedNamespaces = namespaceService.findAllByClusterIdAndStatus(cluster.id!!);

        val existedNamespacesMap = HashMap<String, Namespace>(existedNamespaces.size);

        val allNamespaces = ArrayList<Namespace>();

        // Find all namespaces of the specified cluster from the persistence
        existedNamespaces.forEach { namespace -> existedNamespacesMap[namespace.name] = namespace }

        // Remove the duplicated namespaces if found
        for (newNamespace in newNamespaces) {
            val namespaceId = newNamespace.name;
            var duplicatedNamespace = existedNamespacesMap.remove(namespaceId);
            if (duplicatedNamespace == null) {
                allNamespaces.add(newNamespace);
            }
        }

        // Add the other existed namespaces
        for (existedNamespace in existedNamespacesMap.values) {
            existedNamespace.status = Namespace.Status.UNKNOWN;
            allNamespaces.add(existedNamespace);
        }

        // refresh echo namespace
        allNamespaces.forEach { this::refreshNamespace }
    }

    fun refreshNamespace(namespace: Namespace) {
        namespaceService.saveOrUpdateNamespace(namespace);
        when (namespace.status) {
            Namespace.Status.ACTIVE -> refreshApplications(namespace)
            else -> return;
        }
    }

    @Transactional
    @EntityListener(type = [EntityType.POST_PERSIST])
    fun refreshApplications(namespace: Namespace) {
        val discoveryService = getDiscoveryService(namespace);

        // Load all applications of the specified namespace from the target infrastructure
        val newApplications = discoveryService.getApplications(namespace);

        if (newApplications.isEmpty()) {
            logger.info(
                "No Application was found in the namespace[id : {} , cluster url : {}]!",
                namespace.name,
                namespace.cluster!!.url
            );
            return;
        }

        val existedApplications = applicationService.findAllByNamespaceId(namespace.id!!);

        // The temp cache for applications that will be removed
        val removedApplicationsMap = HashMap<String, Application>(existedApplications.size);

        // Find all applications of the specified namespace from the persistence
        for (existedApplication in existedApplications) {
            removedApplicationsMap[existedApplication.name] = existedApplication;
        }

        for (newApplication in newApplications) {
            // Remove any duplicated application if found
            removedApplicationsMap.remove(newApplication.name)
            newApplication.namespace = namespace;
            // Refresh each application
            refreshApplication(newApplication);
        }

        // Remove the unknown applications
        applicationService.deleteAll(removedApplicationsMap.values);
    }

    fun refreshApplication(application: Application) {
        // Save or Update each application
        applicationService.saveOrUpdateApplication(application);
        // refresh all application instances of the specified application
        refreshApplicationInstances(application);
    }

    @Transactional
    @EntityListener(type = [EntityType.POST_PERSIST])
    fun refreshApplicationInstances(application: Application) {
        val discoveryService = getDiscoveryService(application);
        // Load all instances of the specified application from the target infrastructure
        val newInstances = discoveryService.getApplicationInstances(application);

        if (newInstances.isEmpty()) {
            logger.warn(
                "No Application Instance was found in the application[name : '{}' , namespace: '{}' , cluster: '{}']",
                application.name,
                application.namespace!!.name,
                application.namespace!!.cluster!!.url,
            );
            return;
        }

        // The temp cache for application instances that will be removed
        val removedInstancesMap: MutableMap<String, ApplicationInstance> = mutableMapOf();

        // Find all applications of the specified namespace from the persistence
        for (instance in applicationInstanceService.findAllByApplicationId(application.id!!)) {
            removedInstancesMap[instance.instanceId] = instance;
        }

        newInstances.forEach { instance ->
            {
                // Remove any duplicated instance if found
                removedInstancesMap.remove(instance.instanceId);
                instance.application = application;
                // Refresh each instance
                refreshApplicationInstance(instance);
            }
        }

        // Remove the unknown application instances
        applicationInstanceService.deleteAll(removedInstancesMap.values);
    }

    fun refreshApplicationInstance(instance: ApplicationInstance) {
        // Save or Update each instance
        applicationInstanceService.saveOrUpdateApplicationInstance(instance);
    }

    override fun afterPropertiesSet() {
        initDiscoveryServicesCache();
    }

    private fun initDiscoveryServicesCache() {
        for (discoveryService in discoveryServices) {
            discoveryServicesCache.put(discoveryService.getClusterType(), discoveryService);
        }
        discoveryServicesCache.put(Cluster.Type.NONE, DummyDiscoveryService());
    }

    internal fun async(action: () -> Unit) {
        asyncTaskExecutor.execute(action);
    }

    private fun getDiscoveryService(application: Application): DiscoveryService {
        return getDiscoveryService(application.namespace!!);
    }

    private fun getDiscoveryService(namespace: Namespace): DiscoveryService {
        return getDiscoveryService(namespace.cluster!!);
    }

    private fun getDiscoveryService(cluster: Cluster): DiscoveryService {
        return getDiscoveryService(cluster.type);
    }

    private fun getDiscoveryService(clusterType: Cluster.Type): DiscoveryService {
        return discoveryServicesCache.getOrDefault(clusterType, DummyDiscoveryService());
    }

    private fun buildInstanceId(serviceInstance: ServiceInstance): String {
        return "${serviceInstance.serviceId}-${serviceInstance.host}:${serviceInstance.port}";
    }
}