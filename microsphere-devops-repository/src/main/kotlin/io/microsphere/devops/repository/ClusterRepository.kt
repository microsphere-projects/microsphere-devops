package io.microsphere.devops.repository

import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.enums.ClusterType
import org.springframework.data.repository.CrudRepository

/**
 * Spring Data Repository for {@link Cluster}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Cluster
 * @since 1.0.0
 */
interface ClusterRepository : CrudRepository<Cluster, Long> {

    fun findByName(name: String): Cluster?;

    fun findAllByType(type: ClusterType): List<Cluster>;
}

