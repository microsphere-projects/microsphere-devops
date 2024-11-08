package io.microsphere.devops.service.application

import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.repository.ClusterRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.System.currentTimeMillis

/**
 * The {@link Cluster} Service
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Cluster
 * @since 1.0.0
 */
@Service
class ClusterService(
    private val clusterRepository: ClusterRepository
) : ClusterRepository by clusterRepository {

    @Transactional
    fun saveOrUpdateCluster(cluster: Cluster): Cluster {
        val url = cluster.url;

        val actualCluster: Cluster = clusterRepository.findByUrl(url) ?: cluster;

        if (actualCluster != cluster) {
            actualCluster.name = cluster.name;
            actualCluster.url = cluster.url;
            actualCluster.type = cluster.type;
            actualCluster.username = cluster.username;
            actualCluster.password = cluster.password;
            actualCluster.description = cluster.description;
            actualCluster.updatedAt = currentTimeMillis();
        }

        return clusterRepository.saveAndFlush(actualCluster);
    }

}