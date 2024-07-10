package io.microsphere.devops.web.config

import io.microsphere.devops.service.application.standalone.ApplicationDataSynchronizer
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile

/**
 *  The Spring {@link Configuration} for Standalone mode
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Configuration
 * @since 1.0.0
 */
@Profile("standalone")
@Configuration(proxyBeanMethods = false)
@Import(value = [ApplicationDataSynchronizer::class])
class StandaloneConfiguration {
}