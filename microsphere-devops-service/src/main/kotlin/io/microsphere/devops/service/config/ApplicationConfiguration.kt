package io.microsphere.devops.service.config

import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.service.application.ApplicationServiceFacade
import org.springframework.beans.factory.ObjectProvider
import org.springframework.cloud.client.discovery.event.InstancePreRegisteredEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.EventListener

/**
 * The Spring Configuration for Application
 */
@Configuration(proxyBeanMethods = false)
class ApplicationConfiguration(
    val clusterProvider: ObjectProvider<Cluster>,
    val namespaceProvider: ObjectProvider<Namespace>,
    val applicationServiceFacade: ApplicationServiceFacade
) {

    @EventListener(InstancePreRegisteredEvent::class)
    fun onInstanceRegisteredEvent(event: InstancePreRegisteredEvent) {
        applicationServiceFacade.saveOrUpdate(clusterProvider.ifUnique, namespaceProvider.ifUnique, event.registration);
    }
}