package io.microsphere.devops.api.entity

import io.microsphere.devops.api.commons.Entity
import io.microsphere.devops.api.commons.Named
import io.microsphere.devops.api.enums.ClusterType
import jakarta.persistence.Column
import jakarta.persistence.Table
import org.hibernate.annotations.DynamicUpdate

/**
 * Application Cluster Entity Class
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Entity
 * @since 1.0.0
 */
@jakarta.persistence.Entity
@Table(name = "app_clusters")
@DynamicUpdate
open class Cluster(
    @Column(unique = true, nullable = false)
    override var name: String,
    @Column(nullable = false)
    var type: ClusterType = ClusterType.KUBERNETES,
    @Column(nullable = false)
    var url: String,
) : Entity(), Named