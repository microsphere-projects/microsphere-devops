package io.microsphere.devops.repository

import io.microsphere.devops.api.application.Cluster
import io.microsphere.devops.api.application.ClusterType
import io.microsphere.devops.api.application.Namespace
import io.microsphere.devops.api.application.Status
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ContextConfiguration

/**
 * {@link ClusterRepository} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see NamespaceRepository
 * @since 1.0.0
 */
@DataJpaTest
@EnableAutoConfiguration
@ContextConfiguration(classes = [RepositoriesTest::class])
@EntityScan(basePackages = ["io.microsphere.devops.api"])
class RepositoriesTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val clusterRepository: ClusterRepository,
    val namespaceRepository: NamespaceRepository
) {

    @Test
    fun testRepositories() {
        var cluster: Cluster = Cluster("Nacos", ClusterType.NACOS);
        cluster.description = "Cluster Description";
        cluster.url = "http://localhost:8848";

        var namespace = Namespace("test", Status.ACTIVE, cluster);
        namespace.description = "test namespace";

        clusterRepository.save(cluster);
        namespaceRepository.save(namespace);

        entityManager.flush();

        var foundCluster = clusterRepository.findByIdOrNull(cluster.id as Long);
        var foundNamespace = namespaceRepository.findByIdOrNull(namespace.id as Long);

        assertThat(foundCluster).isEqualTo(cluster);
        assertThat(foundNamespace).isEqualTo(foundNamespace);
        assertThat(foundNamespace?.cluster).isEqualTo(cluster);
        // assertThat(foundCluster?.namespaces?.get(0)).isEqualTo(foundNamespace);
    }
}