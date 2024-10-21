package io.microsphere.devops.service.application

import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.repository.ClusterRepository
import io.microsphere.devops.repository.NamespaceRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.System.currentTimeMillis

/**
 * The Namespace Service
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Cluster
 * @since 1.0.0
 */
@Service
class NamespaceService(
    private val clusterRepository: ClusterRepository,
    private val namespaceRepository: NamespaceRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) : NamespaceRepository by namespaceRepository, ApplicationEventPublisher by applicationEventPublisher {

    @Transactional
    fun saveNamespace(namespace: Namespace, clusterId: Long): Namespace? {
        // TODO Add the rule to limit the number of namespaces
        val cluster = clusterRepository.findByIdOrNull(clusterId);
        if (cluster == null) {
            return null;
        }
        return saveNamespace(namespace);
    }

    @Transactional
    fun updateNamespace(namespace: Namespace): Namespace? {
        val namespaceId = namespace.id;
        val existedNamespace: Namespace? = findByIdOrNull(namespaceId);
        if (existedNamespace == null) {
            return null;
        }
        existedNamespace.name = namespace.name;
        existedNamespace.status = namespace.status;
        existedNamespace.description = namespace.description;
        existedNamespace.updatedAt = currentTimeMillis();
        return saveNamespace(existedNamespace);
    }

    @Transactional
    fun saveOrUpdateNamespace(namespace: Namespace): Namespace {
        val name = namespace.name;
        val actualNamespace = findByName(name) ?: namespace;
        if (actualNamespace != namespace) {
            actualNamespace.status = namespace.status;
            actualNamespace.description = namespace.description;
            actualNamespace.updatedAt = currentTimeMillis();
            actualNamespace.cluster = namespace.cluster;
        }
        return saveNamespace(actualNamespace);
    }

    fun saveNamespace(namespace: Namespace): Namespace {
        val result = saveAndFlush(namespace);
        publishEvent(result);
        return result;
    }

}