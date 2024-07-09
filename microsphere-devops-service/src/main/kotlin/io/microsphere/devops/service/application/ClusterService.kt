package io.microsphere.devops.service.application

import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.repository.ClusterRepository
import jakarta.persistence.EntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.System.currentTimeMillis

/**
 * The Cluster Service
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Cluster
 * @since 1.0.0
 */
@Service
open class ClusterService(
    private val clusterRepository: ClusterRepository,
    private val entityManager: EntityManager
) {

    @Transactional
    fun updateCluster(cluster: Cluster): Cluster? {
        val clusterId = cluster.id;
        val existedCluster: Cluster? = clusterRepository.findByIdOrNull(clusterId);
        if (existedCluster != null) {
            existedCluster.name = cluster.name;
            existedCluster.url = cluster.url;
            existedCluster.description = cluster.description;
            existedCluster.updatedAt = currentTimeMillis();
            clusterRepository.save(existedCluster);
        }
        return existedCluster;
    }

}