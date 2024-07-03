package io.microsphere.devops.repository

import io.microsphere.devops.api.entity.ApplicationInstance
import org.springframework.data.repository.CrudRepository

/**
 * Spring Data Repository for {@link ApplicationInstance}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ApplicationInstance
 * @since 1.0.0
 */
interface ApplicationInstanceRepository : CrudRepository<ApplicationInstance, Long>