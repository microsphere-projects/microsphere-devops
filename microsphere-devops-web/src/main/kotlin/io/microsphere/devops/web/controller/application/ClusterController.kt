package io.microsphere.devops.web.controller.application

import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.service.application.ClusterService
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
class ClusterController(private val clusterService: ClusterService) {

    @GetMapping("/clusters")
    fun queryClusters() = clusterService.findAll();

    @GetMapping("/cluster/{id}")
    fun queryCluster(@PathVariable id: Long) = clusterService.findByIdOrNull(id);

    @PostMapping("/cluster")
    fun saveCluster(@RequestBody cluster: Cluster) = clusterService.save(cluster);

    @PutMapping("/cluster")
    fun updateCluster(@RequestBody cluster: Cluster) = clusterService.updateCluster(cluster);

    @DeleteMapping("/cluster/{id}")
    fun deleteCluster(@PathVariable id: Long) = clusterService.deleteById(id);
}