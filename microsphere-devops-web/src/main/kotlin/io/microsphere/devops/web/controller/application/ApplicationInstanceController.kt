package io.microsphere.devops.web.controller.applicationInstance

import io.microsphere.devops.api.entity.ApplicationInstance
import io.microsphere.devops.service.application.ApplicationInstanceService
import org.springframework.data.domain.Pageable
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
 * The Spring Web {@link Controller} for {@link ApplicationInstance}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ApplicationInstance
 * @see ApplicationInstanceService
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/app")
class ApplicationInstanceController(private val applicationInstanceService: ApplicationInstanceService) {

    @GetMapping("/instances")
    fun queryApplicationInstances(@RequestParam applicationId: Long, pageable: Pageable) =
        applicationInstanceService.findAllByApplicationId(applicationId, pageable);

    @GetMapping("/instance/{id}")
    fun queryApplicationInstance(@PathVariable id: Long) = applicationInstanceService.findByIdOrNull(id);

    @PostMapping("/instance")
    fun saveApplicationInstance(@RequestParam applicationId: Long, @RequestBody applicationInstance: ApplicationInstance) =
        applicationInstanceService.saveApplicationInstance(applicationId, applicationInstance);

    @PutMapping("/instance")
    fun updateApplicationInstance(@RequestBody applicationInstance: ApplicationInstance) =
        applicationInstanceService.updateApplicationInstance(applicationInstance);

    @DeleteMapping("/instance/{id}")
    fun deleteApplicationInstance(@PathVariable id: Long) = applicationInstanceService.deleteById(id);
}