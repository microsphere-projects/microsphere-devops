package io.microsphere.devops.api.entity

import io.microsphere.devops.api.commons.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate
import java.net.URI

/**
 * Application Instance Entity
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Application
 * @see ServiceInstance
 * @since 1.0.0
 */
@jakarta.persistence.Entity
@Table(name = "app_instances")
@DynamicUpdate
open class ApplicationInstance(

    var instanceId: String,

    var host: String,

    var port: Int,

    var secure: Boolean? = false,

    var uri: URI? = null,

    var metadata: String? = null,

    @ManyToOne(optional = false)
    var application: Application? = null

) : Entity() {

    fun getServiceId() = application?.name

}