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

import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.util.JsonUtils;
import io.microsphere.nacos.client.v1.naming.model.Selector;
import io.microsphere.nacos.client.v1.naming.model.Service;
import io.microsphere.nacos.client.v1.naming.model.ServiceList;

import static io.microsphere.nacos.client.http.HttpMethod.DELETE;
import static io.microsphere.nacos.client.http.HttpMethod.GET;
import static io.microsphere.nacos.client.http.HttpMethod.POST;
import static io.microsphere.nacos.client.http.HttpMethod.PUT;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.METADATA;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.NAMESPACE_ID;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.PAGE_NUMBER;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.PAGE_SIZE;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_GROUP_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_NAME;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_PROTECT_THRESHOLD;
import static io.microsphere.nacos.client.transport.OpenApiRequestParam.SERVICE_SELECTOR;
import static io.microsphere.nacos.client.util.OpenApiUtils.isOkResponse;
import static java.util.Collections.singletonMap;

/**
 * The {@link Service} for <a href="https://nacos.io/en/docs/v1/open-api/">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ServiceClient
 * @see OpenApiClient
 * @since 1.0.0
 */
public class OpenApiServiceClient implements ServiceClient {

    public static final String SERVICE_ENDPOINT = "/v1/ns/service";

    public static final String SERVICES_LIST_ENDPOINT = "/v1/ns/service/list";

    private final OpenApiClient openApiClient;

    public OpenApiServiceClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    @Override
    public boolean createService(Service service) {
        OpenApiRequest request = buildServiceRequest(service, POST);
        return responseMessage(request);
    }

    @Override
    public boolean deleteService(String namespaceId, String groupName, String serviceName) {
        OpenApiRequest request = buildServiceRequest(namespaceId, groupName, serviceName, DELETE);
        return responseMessage(request);
    }

    @Override
    public boolean updateService(Service service) {
        OpenApiRequest request = buildServiceRequest(service, PUT);
        return responseMessage(request);
    }

    @Override
    public Service getService(String namespaceId, String groupName, String serviceName) {
        OpenApiRequest request = buildServiceRequest(namespaceId, groupName, serviceName, GET);
        return this.openApiClient.execute(request, Service.class);
    }

    @Override
    public Page<String> getServiceNames(String namespaceId, String groupName, int pageNumber, int pageSize) {
        OpenApiRequest request = OpenApiRequest.Builder.create(SERVICES_LIST_ENDPOINT)
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(SERVICE_GROUP_NAME, groupName)
                .queryParameter(PAGE_NUMBER, pageNumber)
                .queryParameter(PAGE_SIZE, pageSize)
                .build();
        ServiceList serviceList = this.openApiClient.execute(request, ServiceList.class);
        return new Page<>(serviceList.getCount(), serviceList.getDoms(), pageNumber, pageSize);
    }

    private OpenApiRequest buildServiceRequest(String namespaceId, String groupName, String serviceName, HttpMethod method) {
        OpenApiRequest request = OpenApiRequest.Builder.create(SERVICE_ENDPOINT)
                .method(method)
                .queryParameter(NAMESPACE_ID, namespaceId)
                .queryParameter(SERVICE_GROUP_NAME, groupName)
                .queryParameter(SERVICE_NAME, serviceName)
                .build();
        return request;
    }

    private OpenApiRequest buildServiceRequest(Service service, HttpMethod method) {
        return OpenApiRequest.Builder.create(SERVICE_ENDPOINT)
                .method(method)
                .queryParameter(NAMESPACE_ID, service.getNamespaceId())
                .queryParameter(SERVICE_GROUP_NAME, service.getGroupName())
                .queryParameter(SERVICE_NAME, service.getName())
                .queryParameter(SERVICE_PROTECT_THRESHOLD, service.getProtectThreshold())
                .queryParameter(METADATA, service.getMetadata())
                .queryParameter(SERVICE_SELECTOR, toJSON(service.getSelector()))
                .build();
    }

    private boolean responseMessage(OpenApiRequest request) {
        return isOkResponse(this.openApiClient, request);
    }

    private String toJSON(Selector selector) {
        return selector == null ? null : JsonUtils.toJSON(singletonMap("type", selector.getType()));
    }
}
