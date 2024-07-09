package io.microsphere.devops.web.controller.application

import io.microsphere.devops.api.entity.Cluster
import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.repository.ClusterRepository
import io.microsphere.devops.repository.NamespaceRepository
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
 * The Spring Web {@link Controller} for {@link Namespace}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Namespace
 * @see NamespaceRepository
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/app")
class NamespaceController(private val namespaceRepository: NamespaceRepository) {

    @GetMapping("/namespaces")
    fun queryNamespaces() = namespaceRepository.findAll();

    @GetMapping("/namespace/{id}")
    fun queryNamespace(@PathVariable id: Long) = namespaceRepository.findByIdOrNull(id);

    @PostMapping("/namespace")
    fun saveNamespace(@RequestBody namespace: Namespace) = namespaceRepository.save(namespace);

    @PutMapping("/namespace")
    fun updateNamespace(@RequestBody namespace: Namespace) = namespaceRepository.save(namespace);

    @DeleteMapping("/namespace/{id}")
    fun deleteNamespace(@PathVariable id: Long) = namespaceRepository.deleteById(id);
}