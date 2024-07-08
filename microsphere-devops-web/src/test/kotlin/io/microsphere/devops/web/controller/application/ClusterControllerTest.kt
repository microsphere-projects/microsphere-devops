package io.microsphere.devops.web.controller.application

import io.microsphere.devops.api.entity.Cluster
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.boot.test.web.client.TestRestTemplate

/**
 * {@link ClusterController} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ClusterController
 * @since 1.0.0
 */
@EnableAutoConfiguration
@SpringBootTest(
    classes = [ClusterControllerTest::class],
    webEnvironment = WebEnvironment.RANDOM_PORT
)
class ClusterControllerTest(@Autowired val restTemplate: TestRestTemplate) {

    @BeforeEach
    fun setup() {
        println(">> Setup")
    }

    @Test
    fun testSaveCluster() {
        val cluster = Cluster(name = "Kubernetes", url = "http://10.0.0.1");
        cluster.description = "Kubernetes Cluster";
        val clusterFromResponse = restTemplate.postForObject("/api/app/cluster", cluster, Cluster::class.java);
        assertEquals(cluster.name, clusterFromResponse.name);
        assertEquals(cluster.type, clusterFromResponse.type);
        assertEquals(cluster.description, clusterFromResponse.description);
    }
}