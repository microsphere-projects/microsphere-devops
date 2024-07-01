package io.microsphere.devops.api.application

import io.microsphere.devops.api.commons.Entity
import io.microsphere.devops.api.commons.Named

/**
 * Application Cluster Entity Class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Entity
 * @since 1.0.0
 */
data class Cluster(override val id: Long, override var name: String, val type: ClusterType) : Entity, Named {

    lateinit var url: String;
}