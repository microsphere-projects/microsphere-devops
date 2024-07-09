package io.microsphere.devops.service.application

import io.microsphere.devops.api.entity.Application
import io.microsphere.devops.repository.ApplicationRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.System.currentTimeMillis

/**
 * The {@link Application} Service
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Application
 * @since 1.0.0
 */
@Service
class ApplicationService(
    private val applicationRepository: ApplicationRepository,
    private val namespaceService: NamespaceService,
) : ApplicationRepository by applicationRepository {

    @Transactional
    fun saveApplication(namespaceId: Long, application: Application): Application? {
        val namespace = namespaceService.findByIdOrNull(namespaceId);
        if (namespace == null) {
            return null;
        }
        application.namespace = namespace;
        return applicationRepository.save(application);
    }

    @Transactional
    fun updateApplication(application: Application): Application? {
        val id = application?.id;
        var existedApplication = applicationRepository.findByIdOrNull(id);

        if (existedApplication == null) {
            return null;
        }

        existedApplication.name = application.name;
        existedApplication.description = application.description;
        existedApplication.updatedAt = currentTimeMillis();

        return applicationRepository.save(existedApplication);
    }

    fun queryApplications(namespaceId: Long, pageable: Pageable) = findAllByNamespaceId(namespaceId, pageable);
}