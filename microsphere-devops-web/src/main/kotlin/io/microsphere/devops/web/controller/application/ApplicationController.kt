package io.microsphere.devops.web.controller.application

import io.microsphere.devops.api.entity.Application
import io.microsphere.devops.service.application.ApplicationService
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
 * The Spring Web {@link Controller} for {@link Application}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Application
 * @see ApplicationRepository
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/app")
class ApplicationController(private val applicationService: ApplicationService) {

    @GetMapping("/applications")
    fun queryApplications(@RequestParam namespaceId: Long, pageable: Pageable) =
        applicationService.queryApplications(namespaceId, pageable);

    @GetMapping("/application/{id}")
    fun queryApplication(@PathVariable id: Long) = applicationService.findByIdOrNull(id);

    @PostMapping("/application")
    fun saveApplication(@RequestParam namespaceId: Long, @RequestBody application: Application) =
        applicationService.saveApplication(namespaceId, application);

    @PutMapping("/application")
    fun updateApplication(@RequestBody application: Application) = applicationService.updateApplication(application);

    @DeleteMapping("/application/{id}")
    fun deleteApplication(@PathVariable id: Long) = applicationService.deleteById(id);
}