package io.microsphere.devops.service.config.nacos

import com.alibaba.cloud.nacos.NacosDiscoveryProperties
import com.alibaba.nacos.api.common.Constants.DEFAULT_NAMESPACE_ID
import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.condition.NacosProfile
import io.microsphere.devops.condition.StandaloneProfile
import io.microsphere.devops.repository.ClusterRepository
import io.microsphere.devops.service.nacos.NacosService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.util.StringUtils.hasText

@NacosProfile
@Configuration(proxyBeanMethods = false)
class NacosConfiguration(
    val nacosDiscoveryProperties: NacosDiscoveryProperties
) {

    @Bean
    fun cluster(): Cluster {
        val url = nacosDiscoveryProperties.serverAddr;
        val clusterType = Cluster.Type.NACOS;
        val userName = nacosDiscoveryProperties.username;
        val password = nacosDiscoveryProperties.password;
        var cluster = Cluster(clusterType.name + " Cluster", clusterType, url, userName, password);
        cluster.description = clusterType.description;
        return cluster;
    }

    @Bean
    fun namespace(cluster: Cluster): Namespace {
        // Namespace
        var ns = nacosDiscoveryProperties.namespace;
        if (!hasText(ns)) {
            ns = DEFAULT_NAMESPACE_ID;
        }
        return Namespace(ns, Namespace.Status.ACTIVE, cluster);
    }

    @StandaloneProfile
    @Configuration
    class StandaloneConfig(
        val clusterRepository: ClusterRepository,
        val nacosService: NacosService
    ) {

        @Scheduled(fixedRate = 10 * 1000)
        fun sync() {
            val nacosClusters = clusterRepository.findAllByType(Cluster.Type.NACOS);
            nacosService.initClusters(nacosClusters);
        }
    }

}

