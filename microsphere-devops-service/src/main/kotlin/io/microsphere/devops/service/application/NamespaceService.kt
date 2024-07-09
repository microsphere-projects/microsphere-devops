package io.microsphere.devops.service.application

import io.microsphere.devops.api.entity.Namespace
import io.microsphere.devops.repository.ClusterRepository
import io.microsphere.devops.repository.NamespaceRepository
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
open class NamespaceService(
    private val clusterRepository: ClusterRepository,
    private val namespaceRepository: NamespaceRepository
) : NamespaceRepository by namespaceRepository {

    @Transactional
    fun saveNamespace(namespace: Namespace, clusterId: Long): Namespace? {
        // TODO Add the rule to limit the number of namespaces
        val cluster = clusterRepository.findByIdOrNull(clusterId);
        if (cluster == null) {
            return null;
        }
        namespace.cluster = cluster;
        return namespaceRepository.save(namespace);
    }

    @Transactional
    fun updateNamespace(namespace: Namespace): Namespace? {
        val namespaceId = namespace.id;
        val existedNamespace: Namespace? = namespaceRepository.findByIdOrNull(namespaceId);
        if (existedNamespace == null) {
            return null;
        }
        existedNamespace.name = namespace.name;
        existedNamespace.status = namespace.status;
        existedNamespace.description = namespace.description;
        existedNamespace.updatedAt = currentTimeMillis();
        return namespaceRepository.save(existedNamespace);
    }

}