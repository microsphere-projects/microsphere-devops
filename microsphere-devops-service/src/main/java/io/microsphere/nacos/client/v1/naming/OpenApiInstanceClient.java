/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.microsphere.nacos.client.v1.naming;

import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.naming.model.BaseInstance;
import io.microsphere.nacos.client.v1.naming.model.BatchMetadataResult;
import io.microsphere.nacos.client.v1.naming.model.DeleteInstance;
import io.microsphere.nacos.client.v1.naming.model.GenericInstance;
import io.microsphere.nacos.client.v1.naming.model.Instance;
import io.microsphere.nacos.client.v1.naming.model.InstancesList;
import io.microsphere.nacos.client.v1.naming.model.NewInstance;
import io.microsphere.nacos.client.v1.naming.model.QueryInstance;
import io.microsphere.nacos.client.v1.naming.model.Service;
import io.microsphere.nacos.client.v1.naming.model.UpdateHealthInstance;
import io.microsphere.nacos.client.v1.naming.model.UpdateInstance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.microsphere.nacos.client.constants.Constants.DEFAULT_CLUSTER_NAME;
import static io.microsphere.nacos.client.constants.Constants.GROUP_SERVICE_NAME_SEPARATOR;
import static io.microsphere.nacos.client.http.HttpMethod.DELETE;
import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.http.HttpMethod.PUT;
import static io.microsphere.nacos.client.util.JsonUtils.toJSON;
import static io.microsphere.nacos.client.util.OpenApiUtils.isOkResponse;
import static io.microsphere.nacos.client.util.StringUtils.collectionToCommaDelimitedString;
import static io.microsphere.nacos.client.v1.naming.ConsistencyType.EPHEMERAL;
import static java.lang.String.format;

/**
 * The {@link Service} {@link Instance} for <a href="https://nacos.io/en/docs/v1/open-api/">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see InstanceClient
 * @see OpenApiClient
 * @since 1.0.0
 */
public class OpenApiInstanceClient implements InstanceClient {

    private static final String METADATA_BATCH_ENDPOINT = "/v1/ns/instance/metadata/batch";

    public static final String INSTANCE_ENDPOINT = "/v1/ns/instance";

    public static final String HEALTH_INSTANCE_ENDPOINT = "/v1/ns/health/instance";

    private final OpenApiClient openApiClient;

    public OpenApiInstanceClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    @Override
    public boolean register(NewInstance newInstance) {
        OpenApiRequest request = requestBuilder(newInstance, POST).build();
        return responseMessage(request);
    }

    @Override
    public boolean deregister(DeleteInstance instance) {
        OpenApiRequest request = requestBuilder(instance, DELETE).build();
        return responseMessage(request);
    }

    @Override
    public boolean refresh(UpdateInstance updateInstance) {
        OpenApiRequest request = requestBuilder(updateInstance, PUT).build();
        return responseMessage(request);
    }

    @Override
    public InstancesList getInstancesList(String namespaceId, String groupName, String serviceName, Set<String> clusters, boolean healthyOnly) {
        OpenApiRequest request = OpenApiRequest.Builder.create("/v1/ns/instance/list")
                .queryParameter("namespaceId", namespaceId)
                .queryParameter("groupName", groupName)
                .queryParameter("serviceName", serviceName)
                .queryParameter("clusters", collectionToCommaDelimitedString(clusters))
                .queryParameter("healthyOnly", healthyOnly)
                .build();
        return this.openApiClient.execute(request, InstancesList.class);
    }

    @Override
    public Instance getInstance(QueryInstance queryInstance) {
        OpenApiRequest request = requestBuilder(queryInstance, GET).build();
        return this.openApiClient.execute(request, Instance.class);
    }

    @Override
    public boolean updateHealth(UpdateHealthInstance updateHealthInstance) {
        OpenApiRequest request = buildRequest(updateHealthInstance, PUT);
        return responseMessage(request);
    }

    @Override
    public BatchMetadataResult batchUpdateMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType) {
        return batchMetadata(instances, metadata, consistencyType, PUT);
    }

    @Override
    public BatchMetadataResult batchDeleteMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType) {
        return batchMetadata(instances, metadata, consistencyType, DELETE);
    }

    private BatchMetadataResult batchMetadata(Iterable<Instance> instances, Map<String, String> metadata, ConsistencyType consistencyType, HttpMethod method) {
        OpenApiRequest.Builder requestBuilder = OpenApiRequest.Builder.create(METADATA_BATCH_ENDPOINT)
                .method(method);

        Set<String> namespaceIds = new HashSet<>(2);
        Set<String> serviceNames = new HashSet<>(2);
        List<Map<String, String>> instanceMaps = new LinkedList<>();

        consistencyType = consistencyType == null ? EPHEMERAL : consistencyType;


        for (Instance instance : instances) {
            String namespaceId = instance.getNamespaceId();
            String groupName = instance.getGroupName();
            String serviceName = groupName + GROUP_SERVICE_NAME_SEPARATOR + instance.getServiceName();
            validateDuplication(instance, namespaceIds, "namespaceId", namespaceId);
            validateDuplication(instance, serviceNames, "serviceName", serviceName);
            Map<String, String> instanceMap = buildInstanceMap(instance, consistencyType);
            instanceMaps.add(instanceMap);
        }

        String namespaceId = namespaceIds.iterator().next();
        String serviceName = serviceNames.iterator().next();
        String instancesJSON = toJSON(instanceMaps);
        String metadataJSON = toJSON(metadata);

        requestBuilder
                .queryParameter("namespaceId", namespaceId)
                .queryParameter("serviceName", serviceName)
                .queryParameter("consistencyType", consistencyType.getValue())
                .queryParameter("instances", instancesJSON)
                .queryParameter("metadata", metadataJSON);

        OpenApiRequest request = requestBuilder.build();
        return this.openApiClient.execute(request, BatchMetadataResult.class);
    }

    private Map<String, String> buildInstanceMap(Instance instance, ConsistencyType consistencyType) {
        Map<String, String> map = new HashMap<>(4);
        boolean ephemeral = instance.getEphemeral() == null ? EPHEMERAL.equals(consistencyType) : instance.getEphemeral();
        String clusterName = instance.getClusterName() == null ? DEFAULT_CLUSTER_NAME : instance.getClusterName();
        map.put("ip", instance.getIp());
        map.put("port", String.valueOf(instance.getPort()));
        map.put("ephemeral", String.valueOf(ephemeral));
        map.put("clusterName", clusterName);
        return map;
    }

    private void validateDuplication(Instance instance, Set<String> values, String key, String value) {
        values.add(value);
        if (values.size() > 1) {
            String errorMessage = format("Instance[ip : %s , port : %d] with duplicated '%s' : %s",
                    instance.getIp(), instance.getPort(), key, value);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private OpenApiRequest.Builder requestBuilder(NewInstance instance, HttpMethod method) {
        return requestBuilder((GenericInstance) instance, method)
                .queryParameter("healthy", instance.getHealthy());
    }

    private OpenApiRequest buildRequest(UpdateHealthInstance instance, HttpMethod method) {
        return OpenApiRequest.Builder.create(HEALTH_INSTANCE_ENDPOINT)
                .method(method)
                .queryParameter("namespaceId", instance.getNamespaceId())
                .queryParameter("groupName", instance.getGroupName())
                .queryParameter("serviceName", instance.getServiceName())
                .queryParameter("clusterName", instance.getClusterName())
                .queryParameter("ip", instance.getIp())
                .queryParameter("port", instance.getPort())
                .queryParameter("healthy", instance.isHealthy())
                .build();
    }

    private OpenApiRequest.Builder requestBuilder(GenericInstance instance, HttpMethod method) {
        return requestBuilder((BaseInstance) instance, method)
                .queryParameter("weight", instance.getWeight())
                .queryParameter("enabled", instance.getEnabled())
                .queryParameter("ephemeral", instance.getEphemeral())
                .queryParameter("metadata", instance.getMetadataAsJSON());
    }

    private OpenApiRequest.Builder requestBuilder(BaseInstance instance, HttpMethod method) {
        return OpenApiRequest.Builder.create(INSTANCE_ENDPOINT)
                .method(method)
                .queryParameter("namespaceId", instance.getNamespaceId())
                .queryParameter("groupName", instance.getGroupName())
                .queryParameter("serviceName", instance.getServiceName())
                .queryParameter("clusterName", instance.getClusterName())
                .queryParameter("ip", instance.getIp())
                .queryParameter("port", instance.getPort());
    }

    private boolean responseMessage(OpenApiRequest request) {
        return isOkResponse(this.openApiClient, request);
    }
}
