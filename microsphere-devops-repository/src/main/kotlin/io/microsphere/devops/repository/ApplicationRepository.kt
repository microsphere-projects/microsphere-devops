package io.microsphere.devops.repository

import io.microsphere.devops.api.entity.Application
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Spring Data Repository for {@link Application}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Application
 * @since 1.0.0
 */
interface ApplicationRepository : JpaRepository<Application, Long> {

    fun findAllByNamespaceId(namespaceId: Long, pageable: Pageable): Page<Application>;

}