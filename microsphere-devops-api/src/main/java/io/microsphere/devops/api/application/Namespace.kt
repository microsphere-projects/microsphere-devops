package io.microsphere.devops.api.application

import io.microsphere.devops.api.commons.Entity
import io.microsphere.devops.api.commons.Named
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * Application Namespace Entity Class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Entity
 * @since 1.0.0
 */
@jakarta.persistence.Entity
@Table(name = "app_namespaces")
open class Namespace(
    override var name: String,
    var status: Status = Status.ACTIVE,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    var cluster: Cluster
) : Entity(), Named