package io.microsphere.devops.repository

import io.microsphere.devops.api.entity.Cluster
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Spring Data Repository for {@link Cluster}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Cluster
 * @since 1.0.0
 */
interface ClusterRepository : JpaRepository<Cluster, Long> {

    fun findByName(name: String): Cluster?;

    fun findAllByType(type: Cluster.Type): List<Cluster>;
}

