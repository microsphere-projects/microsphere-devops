package io.microsphere.devops.service.config.nacos

import com.alibaba.cloud.nacos.NacosDiscoveryProperties
import com.alibaba.nacos.api.common.Constants.DEFAULT_NAMESPACE_ID
import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.service.application.ApplicationServiceFacade
import io.microsphere.devops.service.config.nacos.NacosConfiguration.ApplicationConfig
import org.springframework.cloud.client.discovery.event.InstancePreRegisteredEvent
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.util.StringUtils.hasText

@Profile("nacos")
@Configuration(proxyBeanMethods = false)
@Import(value = [ApplicationConfig::class])
class NacosConfiguration {

    class ApplicationConfig(
        val nacosDiscoveryProperties: NacosDiscoveryProperties,
        var applicationServiceFacade: ApplicationServiceFacade
    ) {

        @EventListener(InstancePreRegisteredEvent::class)
        fun onInstanceRegisteredEvent(event: InstancePreRegisteredEvent) {
            val url = nacosDiscoveryProperties.serverAddr;
            val clusterType = Cluster.Type.NACOS;
            val userName = nacosDiscoveryProperties.username;
            val password = nacosDiscoveryProperties.password;

            // Cluster
            var cluster = Cluster(clusterType.name + " Cluster", clusterType, url, userName, password);
            cluster.description = clusterType.description;

            // Namespace
            var ns = nacosDiscoveryProperties.namespace;
            if (!hasText(ns)) {
                ns = DEFAULT_NAMESPACE_ID;
            }
            var namespace = Namespace(ns, Namespace.Status.ACTIVE, cluster);

            applicationServiceFacade.saveOrUpdate(cluster, namespace, event.registration);
        }
    }

}

