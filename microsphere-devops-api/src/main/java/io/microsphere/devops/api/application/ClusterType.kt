package io.microsphere.devops.api.application

/**
 * The Application Cluster Type
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
enum class ClusterType(open var label: String) {

    NACOS("Nacos"),

    EUREKA("Eureka"),

    ZOOKEEPER("Zookeeper"),

    CONSUL("Consul"),

    KUBERNETES("Kubernetes");
}
