package io.microsphere.devops.api.entity

import io.microsphere.devops.api.commons.Entity
import jakarta.persistence.FetchType.LAZY
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

    var uri: URI? = null,

    var metadata: String? = null,

    var status: Status = Status.UP,

    @ManyToOne(optional = false, fetch = LAZY)
    var application: Application? = null

) : Entity() {

    fun getServiceId() = application?.name

    fun getScheme() = uri?.scheme;

    fun isSecure() = "https" == getScheme()

    /**
     * The {@link ApplicationInstance}s' Status
     */
    enum class Status {

        UNKNOWN,

        UP,

        DOWN,

        OUT_OF_SERVICE
    }

}