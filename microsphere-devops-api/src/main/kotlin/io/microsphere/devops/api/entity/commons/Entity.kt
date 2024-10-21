package io.microsphere.devops.api.commons

import io.microsphere.devops.api.event.GenericEntityListener
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.lang.System.currentTimeMillis

/**
 * The Commons Entity Data Class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
@MappedSuperclass
@EntityListeners(GenericEntityListener::class)
open class Entity(

    /**
     * ID property
     */
    @Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long? = null,

    /**
     * Description property
     */
    var description: String? = null,

    /**
     * Created At property(timestamp)
     */
    var createdAt: Long = currentTimeMillis(),

    /**
     * Updated At property(timestamp)
     */
    var updatedAt: Long = currentTimeMillis(),
)
