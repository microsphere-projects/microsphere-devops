package io.microsphere.devops.repository

import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.api.entity.Namespace.Status
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Spring Data Repository for {@link Namespace}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Namespace
 * @since 1.0.0
 */
interface NamespaceRepository : JpaRepository<Namespace, Long> {

    fun findByName(name: String): Namespace?;

    fun findAllByClusterIdAndStatus(id: Long, status: Status = Status.ACTIVE): List<Namespace>;
}