package io.microsphere.devops.api.application

import io.microsphere.devops.api.commons.Entity
import io.microsphere.devops.api.commons.Named
import jakarta.persistence.CascadeType
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
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
    @OneToMany(
        mappedBy = "cluster",
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        targetEntity = Namespace::class
    )
    var namespaces: MutableList<Namespace>? = mutableListOf()
) : Entity(), Named