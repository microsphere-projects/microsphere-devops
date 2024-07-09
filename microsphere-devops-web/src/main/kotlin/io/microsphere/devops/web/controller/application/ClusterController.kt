package io.microsphere.devops.web.controller.application

import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.repository.ClusterRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * The Spring Web {@link Controller} for {@link Cluster}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Cluster
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/app")
class ClusterController(private val clusterRepository: ClusterRepository) {

    @GetMapping("/clusters")
    fun queryClusters() = clusterRepository.findAll();

    @GetMapping("/cluster/{id}")
    fun queryCluster(@PathVariable id: Long) = clusterRepository.findByIdOrNull(id);

    @PostMapping("/cluster")
    fun saveCluster(@RequestBody cluster: Cluster) = clusterRepository.save(cluster);

    @PutMapping("/cluster")
    fun updateCluster(@RequestBody cluster: Cluster) = clusterRepository.save(cluster);

    @DeleteMapping("/cluster")
    fun deleteCluster(id: Long) = clusterRepository.deleteById(id);
}