package io.microsphere.devops.api.application

/**
 * The Application Cluster Type
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
enum class ClusterType(var value: String) {
    NACOS("Nacos"),

    EUREKA("Eureka"),

    ZOOKEEPER("Zookeeper"),

    CONSUL("Consul"),

    KUBERNETES("Kubernetes");

    /**
     * Get the value of application Cluster Type
     */
    fun getValue(): String {
        return value;
    }
}
