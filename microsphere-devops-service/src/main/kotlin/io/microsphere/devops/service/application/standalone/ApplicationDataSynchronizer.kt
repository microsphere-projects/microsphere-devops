package io.microsphere.devops.service.application.standalone

import io.microsphere.devops.service.application.ApplicationInstanceService
import io.microsphere.devops.service.application.ApplicationService
import io.microsphere.devops.service.application.ClusterService
import io.microsphere.devops.service.application.NamespaceService
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.scheduling.annotation.Scheduled
import kotlin.collections.forEach

/**
 * The Synchronizer class for Application Data
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ApplicationService
 * @see ApplicationInstanceService
 * @see DiscoveryClient
 * @since 1.0.0
 */
open class ApplicationDataSynchronizer(
    private val clusterService: ClusterService,
    private val namespaceService: NamespaceService,
    private val applicationService: ApplicationService,
    private val applicationInstanceService: ApplicationInstanceService,
    private val discoveryClient: DiscoveryClient
) {

    @Scheduled(fixedRate = 60 * 1000)
    fun sync() {
        val services = discoveryClient.services
        services.forEach { serviceId ->
            val instances = discoveryClient.getInstances(serviceId)
            instances.forEach { instance ->

            }
        }
    }
}