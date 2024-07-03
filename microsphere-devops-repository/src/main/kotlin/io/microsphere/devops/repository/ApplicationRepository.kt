package io.microsphere.devops.repository

import io.microsphere.devops.api.entity.Application
import org.springframework.data.repository.CrudRepository

/**
 * Spring Data Repository for {@link Application}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Application
 * @since 1.0.0
 */
interface ApplicationRepository : CrudRepository<Application, Long>