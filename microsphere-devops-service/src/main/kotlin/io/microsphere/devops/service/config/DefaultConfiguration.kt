package io.microsphere.devops.web.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * The Spring {@link Configuration} for Default
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Configuration
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableAsync
@EnableScheduling
@EnableCaching
@EnableTransactionManagement
class DefaultConfiguration {

}

var defaultBeans = beans {

    bean(DEFAULT_TASK_EXECUTOR_BEAN_NAME) {
        ThreadPoolTaskScheduler().apply {
            val availableProcessors = Runtime.getRuntime().availableProcessors();
            threadNamePrefix = "microsphere-devops-thread-";
            isDaemon = true;
            poolSize = availableProcessors;
        }
    }

}
