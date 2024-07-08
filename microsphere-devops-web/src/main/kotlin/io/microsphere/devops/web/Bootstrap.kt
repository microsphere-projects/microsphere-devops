package io.microsphere.devops.web

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Microsphere DevOps Service Bootstrap
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EnableAutoConfiguration
 * @since 1.0.0
 */
@EntityScan(basePackages = ["io.microsphere.devops.api.entity"])
@EnableJpaRepositories(basePackages = ["io.microsphere.devops.repository"])
@ComponentScan(basePackages = ["io.microsphere.devops.web.controller"])
@EnableAutoConfiguration
open class Bootstrap

fun main(args: Array<String>) {
    runApplication<Bootstrap>(*args)
}
