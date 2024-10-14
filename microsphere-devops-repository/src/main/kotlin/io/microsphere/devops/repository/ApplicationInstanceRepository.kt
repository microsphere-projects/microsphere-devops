package io.microsphere.devops.repository

import io.microsphere.devops.api.entity.ApplicationInstance
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Spring Data Repository for {@link ApplicationInstance}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ApplicationInstance
 * @since 1.0.0
 */
interface ApplicationInstanceRepository : JpaRepository<ApplicationInstance, Long> {

    fun findAllByApplicationId(applicationId: Long, pageable: Pageable): Page<ApplicationInstance>;

    fun findByInstanceId(instanceId: String): ApplicationInstance?;
}