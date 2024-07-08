package io.microsphere.devops.repository

import io.microsphere.devops.api.entity.ApplicationInstance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * Spring Data Repository for {@link ApplicationInstance}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ApplicationInstance
 * @since 1.0.0
 */
interface ApplicationInstanceRepository : JpaRepository<ApplicationInstance, Long>