package io.microsphere.devops.api.entity

import org.springframework.cloud.client.DefaultServiceInstance

/**
 * Application Instance Entity
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see DefaultServiceInstance
 * @since 1.0.0
 */
class ApplicationInstance(val id : UInt,val createdTime: Long, var updatedTime: Long) : DefaultServiceInstance() {

}