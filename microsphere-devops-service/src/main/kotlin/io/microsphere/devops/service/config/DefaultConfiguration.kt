package io.microsphere.devops.web.config

import io.microsphere.devops.repository.event.PublishingTransactionalEventEntityListener
import io.microsphere.spring.data.jpa.annotation.EnableJpaExtension
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.support.beans
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor.DEFAULT_TASK_EXECUTOR_BEAN_NAME
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor.DEFAULT_TASK_SCHEDULER_BEAN_NAME
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler
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
@EnableJpaExtension
class DefaultConfiguration {

}

var defaultBeans = beans {

    bean(DEFAULT_TASK_EXECUTOR_BEAN_NAME) {
        SimpleAsyncTaskScheduler().apply {
            threadNamePrefix = "microsphere-devops-task-thread-";
            setVirtualThreads(true);
            isDaemon = true;
        }
    }

    bean(DEFAULT_TASK_SCHEDULER_BEAN_NAME) {
        ThreadPoolTaskScheduler().apply {
            val availableProcessors = Runtime.getRuntime().availableProcessors();
            threadNamePrefix = "microsphere-devops-scheduling-thread-";
            isDaemon = true;
            poolSize = availableProcessors;
        }
    }

}
