package io.microsphere.devops.web.controller.application

import io.microsphere.devops.api.entity.Application
import io.microsphere.devops.repository.ApplicationRepository
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
 * The Spring Web {@link Controller} for {@link Application}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Application
 * @see ApplicationRepository
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/app")
class ApplicationController(private val applicationRepository: ApplicationRepository) {

    @GetMapping("/applications")
    fun queryApplications() = applicationRepository.findAll();

    @GetMapping("/application/{id}")
    fun queryApplication(@PathVariable id: Long) = applicationRepository.findByIdOrNull(id);

    @PostMapping("/application")
    fun saveApplication(@RequestBody application: Application) = applicationRepository.save(application);

    @PutMapping("/application")
    fun updateApplication(@RequestBody application: Application) = applicationRepository.save(application);

    @DeleteMapping("/application/{id}")
    fun deleteApplication(@PathVariable id: Long) = applicationRepository.deleteById(id);
}