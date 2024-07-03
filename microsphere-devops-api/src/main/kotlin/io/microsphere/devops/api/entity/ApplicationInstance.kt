package io.microsphere.devops.api.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.cloud.client.DefaultServiceInstance
import java.lang.System.currentTimeMillis

/**
 * Application Instance Entity
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Application
 * @see ServiceInstance
 * @see org.springframework.cloud.client.DefaultServiceInstance
 * @since 1.0.0
 */
@Entity
@Table(name = "app_instances")
open class ApplicationInstance(
    /**
     * ID property
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,

    /**
     * Created At property(timestamp)
     */
    var createdAt: Long = currentTimeMillis(),

    /**
     * Updated At property(timestamp)
     */
    var updatedAt: Long = currentTimeMillis(),

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    val application: Application
) : DefaultServiceInstance() {

}