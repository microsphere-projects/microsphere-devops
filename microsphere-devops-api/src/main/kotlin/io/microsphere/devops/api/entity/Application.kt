package io.microsphere.devops.api.entity

import io.microsphere.devops.api.commons.Entity
import io.microsphere.devops.api.commons.Named
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * Application Entity
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ApplicationInstance
 * @since 1.0.0
 */
@jakarta.persistence.Entity
@Table(name = "applications")
open class Application(
    override var name: String,
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    var namespace: Namespace
) : Entity(), Named {

}