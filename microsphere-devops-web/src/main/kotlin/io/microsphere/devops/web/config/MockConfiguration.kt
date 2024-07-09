package io.microsphere.devops.web.config

import io.microsphere.devops.api.entity.Application
import io.microsphere.devops.api.entity.ApplicationInstance
import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.api.enums.ClusterType
import io.microsphere.devops.api.enums.Status
import io.microsphere.devops.service.application.ApplicationInstanceService
import io.microsphere.devops.service.application.ApplicationService
import io.microsphere.devops.service.application.ClusterService
import io.microsphere.devops.service.application.NamespaceService
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.net.URI

/**
 * Mock Configuration
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see TODO
 * @since 1.0.0
 */
@Profile("mock")
@Configuration(proxyBeanMethods = false)
class MockConfiguration(
    private val clusterService: ClusterService,
    private val namespaceService: NamespaceService,
    private val applicationService: ApplicationService,
    private val applicationInstanceService: ApplicationInstanceService
) {

    @PostConstruct
    fun mock() {
        for (clusterType in ClusterType.values()) {
            val clusterName = "${clusterType.value} Cluster";
            val cluster = Cluster(clusterName, clusterType, "mock://127.0.0.1");
            clusterService.saveAndFlush(cluster);
            for (i in 1..10) {
                val namespaceName = "${clusterName}-Namespace-$i";
                val namespace = Namespace(namespaceName, Status.ACTIVE, cluster);
                namespaceService.saveAndFlush(namespace);
                for (j in 1..5) {
                    val applicationName = "${namespaceName}-Application-$i";
                    val application = Application(applicationName, namespace);
                    applicationService.saveAndFlush(application);
                    for (k in 1..20) {
                        val instanceId = "$applicationName-$k";
                        val host = "127.0.0.1";
                        val port = 8080;
                        val isSecure = false;
                        val uri = URI("http://${host}:${port}/");
                        val applicationInstance =
                            ApplicationInstance(instanceId, host, port, isSecure, uri, application = application);
                        applicationInstanceService.saveAndFlush(applicationInstance);
                    }
                }
            }
        }
    }

}