package io.microsphere.devops.api.entity

import io.microsphere.devops.api.commons.Entity
import io.microsphere.devops.api.commons.Named
import io.microsphere.devops.api.enums.Status
import jakarta.persistence.Column
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

/**
 * Application Namespace Entity Class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Entity
 * @since 1.0.0
 */
@jakarta.persistence.Entity
@Table(name = "app_namespaces")
@DynamicUpdate
open class Namespace(
    @Column(nullable = false)
    override var name: String,
    @Column(nullable = false)
    var status: Status = Status.ACTIVE,
    @ManyToOne(optional = false)
    var cluster: Cluster? = null
) : Entity(), Named