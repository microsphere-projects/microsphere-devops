package io.microsphere.devops.repository

import io.microsphere.devops.api.entity.Application
import io.microsphere.devops.api.entity.ApplicationInstance
import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.api.enums.ClusterType
import io.microsphere.devops.api.enums.Status
import junit.framework.TestCase.assertEquals
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
    val namespaceRepository: NamespaceRepository,
    val applicationRepository: ApplicationRepository,
    val applicationInstanceRepository: ApplicationInstanceRepository
) {

    @Test
    fun `Test ClusterRepository CRUD Operations`() {
        for (type in ClusterType.values()) {
            var cluster = Cluster(type.value, type);
            cluster.url = "http://127.0.0.1";
            cluster.description = type.description;
            clusterRepository.save(cluster);
        }
        entityManager.flush();

        for (type in ClusterType.values()) {
            var clusters = clusterRepository.findAllByType(type);
            assertThat(clusters).isNotNull();
            assertEquals(1, clusters.size);

            var cluster = clusters[0];
            assertEquals(type.value, cluster.name);

            var namedCluster = clusterRepository.findByName(cluster.name);
            assertThat(namedCluster).isEqualTo(cluster);
        }
    }

    @Test
    fun testRepositories() {
        var cluster = Cluster("Nacos", ClusterType.NACOS);
        cluster.description = "Cluster Description";
        cluster.url = "http://localhost:8848";

        var namespace = Namespace("test", Status.ACTIVE, cluster);
        namespace.description = "test namespace";

        clusterRepository.save(cluster);
        namespaceRepository.save(namespace);

        for (i in 1..10) {
            val app = Application("test-app-${i}", namespace);
            applicationRepository.save(app);
            for (j in 1..10) {
                var instance = ApplicationInstance(application = app);
                instance.instanceId = "test-app-${i}-instance+${j}";
                instance.host = "127.0.0.1";
                instance.port = 8080;
                applicationInstanceRepository.save(instance);
            }
        }

        entityManager.flush();

        var foundCluster = clusterRepository.findByIdOrNull(cluster.id as Long);
        var foundNamespace = namespaceRepository.findByIdOrNull(namespace.id as Long);



        assertThat(foundCluster).isEqualTo(cluster);
        assertThat(foundNamespace).isEqualTo(foundNamespace);
        assertThat(foundNamespace?.cluster).isEqualTo(cluster);
    }
}