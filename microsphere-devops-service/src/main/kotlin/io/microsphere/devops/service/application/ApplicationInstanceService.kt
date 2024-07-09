package io.microsphere.devops.service.application

import io.microsphere.devops.api.entity.ApplicationInstance
import io.microsphere.devops.repository.ApplicationInstanceRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * The {@link ApplicationInstance} Service
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ApplicationInstanceRepository
 * @see ApplicationInstance
 * @since 1.0.0
 */
@Service
class ApplicationInstanceService(
    private val applicationInstanceRepository: ApplicationInstanceRepository,
    private val applicationService: ApplicationService
) :
    ApplicationInstanceRepository by applicationInstanceRepository {

    fun saveApplicationInstance(applicationId: Long, applicationInstance: ApplicationInstance): ApplicationInstance? {
        val application = applicationService.findByIdOrNull(applicationId);
        if (application == null) {
            return null;
        }
        applicationInstance.application = application;
        return applicationInstanceRepository.save(applicationInstance);
    }

    fun updateApplicationInstance(applicationInstance: ApplicationInstance): ApplicationInstance? {
        val applicationId = applicationInstance?.id;
        val existedApplicationInstance = applicationInstanceRepository.findByIdOrNull(applicationId);

        if (existedApplicationInstance == null) {
            return null;
        }

        existedApplicationInstance.instanceId = applicationInstance.instanceId;
        existedApplicationInstance.host = applicationInstance.host;
        existedApplicationInstance.port = applicationInstance.port;
        existedApplicationInstance.uri = applicationInstance.uri;
        existedApplicationInstance.secure = applicationInstance.secure;
        existedApplicationInstance.metadata = applicationInstance.metadata;

        return applicationInstanceRepository.save(applicationInstance);
    }
}