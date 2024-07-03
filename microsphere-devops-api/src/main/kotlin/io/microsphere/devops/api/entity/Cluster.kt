package io.microsphere.devops.api.entity

import io.microsphere.devops.api.commons.Entity
import io.microsphere.devops.api.commons.Named
import io.microsphere.devops.api.enums.ClusterType
import jakarta.persistence.Table

/**
 * Application Cluster Entity Class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Entity
 * @since 1.0.0
 */
@jakarta.persistence.Entity
@Table(name = "app_clusters")
open class Cluster(
    override var name: String,
    var type: ClusterType? = ClusterType.KUBERNETES,
    var url: String? = null,
) : Entity(), Named