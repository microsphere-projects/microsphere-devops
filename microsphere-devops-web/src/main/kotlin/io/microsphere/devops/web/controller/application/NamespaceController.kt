package io.microsphere.devops.web.controller.application

import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.service.application.NamespaceService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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
class NamespaceController(private val namespaceService: NamespaceService) {

    @GetMapping("/namespaces")
    fun queryNamespaces() = namespaceService.findAll();

    @GetMapping("/namespace/{id}")
    fun queryNamespace(@PathVariable id: Long) = namespaceService.findByIdOrNull(id);

    @PostMapping("/namespace")
    fun saveNamespace(@RequestBody namespace: Namespace, @RequestParam clusterId: Long) =
        namespaceService.saveNamespace(namespace, clusterId);

    @PutMapping("/namespace")
    fun updateNamespace(@RequestBody namespace: Namespace) = namespaceService.updateNamespace(namespace);

    @DeleteMapping("/namespace/{id}")
    fun deleteNamespace(@PathVariable id: Long) = namespaceService.deleteById(id);
}