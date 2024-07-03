package io.microsphere.devops.api.application

/**
 * The Application Cluster Type
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
enum class ClusterType(open var label: String, open val url: String) {

    NACOS("Nacos", "https://img.alicdn.com/imgextra/i1/O1CN01YjDURc26ODF5FQt4d_!!6000000007651-55-tps-123-24.svg"),

    EUREKA("Eureka", "TODO"),

    ZOOKEEPER("Zookeeper", "TODO"),

    CONSUL("Consul", "TODO"),

    KUBERNETES("Kubernetes", "https://kubernetes.io/images/wheel.svg");
}
