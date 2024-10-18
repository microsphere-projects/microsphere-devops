package io.microsphere.devops.web

import io.microsphere.devops.web.config.DefaultConfiguration
import io.microsphere.devops.web.config.defaultBeans
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.support.beans
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

/**
 * Microsphere DevOps Service Bootstrap
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EnableAutoConfiguration
 * @since 1.0.0
 */
@EntityScan(
    basePackages = [
        "io.microsphere.devops.api"
    ]
)
@EnableJpaRepositories(
    basePackages = [
        "io.microsphere.devops.repository"
    ]
)
@ComponentScan(
    basePackages = [
        "io.microsphere.devops.service",
        "io.microsphere.devops.web"
    ]
)
@EnableScheduling
@EnableAutoConfiguration
@EnableDiscoveryClient
open class Bootstrap

fun main(args: Array<String>) {
    runApplication<Bootstrap>(*args) {
        addInitializers(defaultBeans)
    }
}

