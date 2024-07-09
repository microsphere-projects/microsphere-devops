package io.microsphere.devops.web.controller.applicationInstance

import io.microsphere.devops.api.entity.ApplicationInstance
import io.microsphere.devops.repository.ApplicationInstanceRepository
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
 * The Spring Web {@link Controller} for {@link ApplicationInstance}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ApplicationInstance
 * @see ApplicationInstanceRepository
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/app")
class ApplicationInstanceController(private val applicationInstanceRepository: ApplicationInstanceRepository) {

    @GetMapping("/instances")
    fun queryApplicationInstances() = applicationInstanceRepository.findAll();

    @GetMapping("/instance/{id}")
    fun queryApplicationInstance(@PathVariable id: Long) = applicationInstanceRepository.findByIdOrNull(id);

    @PostMapping("/instance")
    fun saveApplicationInstance(@RequestBody applicationInstance: ApplicationInstance) = applicationInstanceRepository.save(applicationInstance);

    @PutMapping("/instance")
    fun updateApplicationInstance(@RequestBody applicationInstance: ApplicationInstance) = applicationInstanceRepository.save(applicationInstance);

    @DeleteMapping("/instance/{id}")
    fun deleteApplicationInstance(@PathVariable id: Long) = applicationInstanceRepository.deleteById(id);
}