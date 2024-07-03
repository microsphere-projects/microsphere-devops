package io.microsphere.devops.api.enums

/**
 * The Application Cluster Type
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
enum class ClusterType(val value: String, val description: String, val logo: String) {
    KUBERNETES("kubernetes",
        "Kubernetes 也称为 K8s, 是用于自动部署、扩缩和管理容器化应用程序的开源系统",
        "https://kubernetes.io/images/wheel.svg"),

    NACOS("Nacos","""
        Nacos 通过提供简单易用的动态服务发现、服务配置、服务共享与管理等服务基础设施，帮助用户在云原生时代，
        在私有云、混合云或者公有云等所有云环境中，更好的构建、交付、管理自己的微服务平台，更快的复用和组合业务服务，
        更快的交付商业创新的价值，从而为用户赢得市场。
    """.trimIndent(),
        "https://img.alicdn.com/imgextra/i1/O1CN01YjDURc26ODF5FQt4d_!!6000000007651-55-tps-123-24.svg"),

    EUREKA("Eureka", """
        Eureka is a RESTful (Representational State Transfer) service that is primarily used in the AWS cloud for the 
        purpose of discovery, load balancing and failover of middle-tier servers. It plays a critical role in Netflix 
        mid-tier infra.
    """.trimIndent(), "eureka.png"),

    ZOOKEEPER("Zookeeper", """
        ZooKeeper is a centralized service for maintaining configuration information, naming, 
        providing distributed synchronization, and providing group services.
    """.trimIndent(),
        "https://zookeeper.apache.org/images/zookeeper_small.gif"),

    CONSUL("Consul", """
        Consul is a multi-networking tool that offers a fully-featured service mesh solution. It solves the networking 
        and security challenges of operating microservices and cloud infrastructure in multi-cloud and hybrid cloud 
        environments.
    """.trimIndent(), "https://djeqr6to3dedg.cloudfront.net/repo-logos/library/consul/live/logo.png"),


}
