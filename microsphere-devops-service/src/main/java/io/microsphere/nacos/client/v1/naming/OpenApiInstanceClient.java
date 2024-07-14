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
import io.microsphere.nacos.client.v1.naming.model.Instance;
import io.microsphere.nacos.client.v1.naming.model.InstancesList;
import io.microsphere.nacos.client.v1.naming.model.Service;

import java.util.Set;

import static io.microsphere.nacos.client.http.HttpMethod.DELETE;
import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.http.HttpMethod.PUT;
import static io.microsphere.nacos.client.util.JsonUtils.toJSON;
import static io.microsphere.nacos.client.util.StringUtils.collectionToCommaDelimitedString;

/**
 * The {@link Service} {@link Instance} for <a href="https://nacos.io/en/docs/v1/open-api/">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see InstanceClient
 * @see OpenApiClient
 * @since 1.0.0
 */
public class OpenApiInstanceClient implements InstanceClient {

    private final OpenApiClient openApiClient;

    public OpenApiInstanceClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    @Override
    public boolean register(Instance instance) {
        OpenApiRequest request = buildInstanceRequest(instance, POST);
        return responseMessage(request);
    }

    @Override
    public boolean deregister(Instance instance) {
        OpenApiRequest request = buildInstanceRequest(instance, DELETE);
        return responseMessage(request);
    }

    @Override
    public boolean refresh(Instance instance) {
        OpenApiRequest request = buildInstanceRequest(instance, PUT);
        return responseMessage(request);
    }

    @Override
    public InstancesList getInstancesList(String namespaceId, String groupName, String serviceName, Set<String> clusters, boolean healthyOnly) {
        OpenApiRequest request = OpenApiRequest.Builder.create("/v1/ns/instance/list")
                .method(GET)
                .queryParameter("namespaceId", namespaceId)
                .queryParameter("groupName", groupName)
                .queryParameter("serviceName", serviceName)
                .queryParameter("clusters", collectionToCommaDelimitedString(clusters))
                .queryParameter("healthyOnly", healthyOnly)
                .build();
        return openApiClient.execute(request, InstancesList.class);
    }

    private OpenApiRequest buildInstanceRequest(Instance instance, HttpMethod method) {
        return OpenApiRequest.Builder.create("/v1/ns/instance")
                .method(method)
                .queryParameter("namespaceId", instance.getNamespaceId())
                .queryParameter("groupName", instance.getGroupName())
                .queryParameter("serviceName", instance.getServiceName())
                .queryParameter("clusterName", instance.getClusterName())
                .queryParameter("instanceId", instance.getInstanceId())
                .queryParameter("ip", instance.getIp())
                .queryParameter("port", instance.getPort())
                .queryParameter("weight", instance.getWeight())
                .queryParameter("enabled", instance.isEnabled())
                .queryParameter("healthy", instance.isHealthy())
                .queryParameter("ephemeral", instance.isEphemeral())
                .queryParameter("metadata", toJSON(instance.getMetadata()))
                .build();
    }

    private boolean responseMessage(OpenApiRequest request) {
        return OpenApiServiceClient.responseMessage(this.openApiClient, request);
    }
}
