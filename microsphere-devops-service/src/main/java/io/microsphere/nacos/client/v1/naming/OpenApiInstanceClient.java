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
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CLUSTERS;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CLUSTER_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.CONSISTENCY_TYPE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCES;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_ENABLED;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_EPHEMERAL;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_HEALTHY;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_HEALTHY_ONLY;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_IP;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.METADATA;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_PORT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.INSTANCE_WEIGHT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.NAMESPACE_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_GROUP_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_NAME;
import static io.microsphere.nacos.client.util.OpenApiUtils.isOkResponse;
import static io.microsphere.nacos.client.util.StringUtils.isBlank;
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

    public static final String INSTANCE_ENDPOINT = "/v1/ns/instance";

    public static final String INSTANCES_LIST_ENDPOINT = "/v1/ns/instance/list";

    public static final String INSTANCE_HEALTH_ENDPOINT = "/v1/ns/health/instance";

    private static final String METADATA_BATCH_ENDPOINT = "/v1/ns/instance/metadata/batch";

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
        OpenApiRequest request = OpenApiRequest.Builder.create(INSTANCES_LIST_ENDPOINT)
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(SERVICE_GROUP_NAME, groupName)
                .queryParameter(SERVICE_NAME, serviceName)
                .queryParameter(CLUSTERS, clusters)
                .queryParameter(INSTANCE_HEALTHY_ONLY, healthyOnly)
                .build();
        InstancesList instancesList = this.openApiClient.execute(request, InstancesList.class);
        List<Instance> instances = instancesList.getHosts();
        completeInstance(instances, namespaceId, groupName, serviceName);
        return instancesList;
    }

    @Override
    public Instance getInstance(QueryInstance queryInstance) {
        OpenApiRequest request = requestBuilder(queryInstance, GET).build();
        Instance instance = this.openApiClient.execute(request, Instance.class);
        completeInstance(instance, queryInstance);
        return instance;
    }

    @Override
    public boolean updateHealth(UpdateHealthInstance updateHealthInstance) {
        OpenApiRequest request = buildHealthRequest(updateHealthInstance, PUT);
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

        requestBuilder
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(SERVICE_NAME, serviceName)
                .queryParameter(CONSISTENCY_TYPE, consistencyType)
                .queryParameter(INSTANCES, instanceMaps)
                .queryParameter(METADATA, metadata);

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
                .queryParameter(INSTANCE_HEALTHY, instance.getHealthy());
    }

    private OpenApiRequest buildHealthRequest(UpdateHealthInstance instance, HttpMethod method) {
        return OpenApiRequest.Builder.create(INSTANCE_HEALTH_ENDPOINT)
                .method(method)
                .queryParameter(NAMESPACE_ID, instance.getNamespaceId())
                .queryParameter(SERVICE_GROUP_NAME, instance.getGroupName())
                .queryParameter(SERVICE_NAME, instance.getServiceName())
                .queryParameter(CLUSTER_NAME, instance.getClusterName())
                .queryParameter(INSTANCE_IP, instance.getIp())
                .queryParameter(INSTANCE_PORT, instance.getPort())
                .queryParameter(INSTANCE_HEALTHY, instance.isHealthy())
                .build();
    }

    private OpenApiRequest.Builder requestBuilder(GenericInstance instance, HttpMethod method) {
        return requestBuilder((BaseInstance) instance, method)
                .queryParameter(INSTANCE_WEIGHT, instance.getWeight())
                .queryParameter(INSTANCE_ENABLED, instance.getEnabled())
                .queryParameter(INSTANCE_EPHEMERAL, instance.getEphemeral())
                .queryParameter(METADATA, instance.getMetadata());
    }

    private OpenApiRequest.Builder requestBuilder(BaseInstance instance, HttpMethod method) {
        return OpenApiRequest.Builder.create(INSTANCE_ENDPOINT)
                .method(method)
                .queryParameter(NAMESPACE_ID, instance.getNamespaceId())
                .queryParameter(SERVICE_GROUP_NAME, instance.getGroupName())
                .queryParameter(SERVICE_NAME, instance.getServiceName())
                .queryParameter(CLUSTER_NAME, instance.getClusterName())
                .queryParameter(INSTANCE_IP, instance.getIp())
                .queryParameter(INSTANCE_PORT, instance.getPort());
    }

    private void completeInstance(Iterable<Instance> instances, String namespaceId, String groupName, String serviceName) {
        for (Instance instance : instances) {
            completeInstance(instance, namespaceId, groupName, serviceName);
        }
    }

    private void completeInstance(Instance instance, BaseInstance baseInstance) {
        String namespaceId = baseInstance.getNamespaceId();
        String groupName = baseInstance.getGroupName();
        String serviceName = baseInstance.getServiceName();
        completeInstance(instance, namespaceId, groupName, serviceName);
    }

    private void completeInstance(Instance instance, String namespaceId, String groupName, String serviceName) {
        if (isBlank(instance.getNamespaceId())) {
            instance.setNamespaceId(namespaceId);
        }
        if (isBlank(instance.getGroupName())) {
            instance.setGroupName(groupName);
        }
        if (isBlank(instance.getServiceName())) {
            instance.setServiceName(serviceName);
        }
    }

    private boolean responseMessage(OpenApiRequest request) {
        return isOkResponse(this.openApiClient, request);
    }
}
