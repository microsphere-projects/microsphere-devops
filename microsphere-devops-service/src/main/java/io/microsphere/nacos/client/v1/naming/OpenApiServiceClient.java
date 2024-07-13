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
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.naming.model.ServiceList;

/**
 * The Service for <a href="https://nacos.io/en/docs/v1/open-api/">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ServiceClient
 * @see OpenApiClient
 * @since 1.0.0
 */
public class OpenApiServiceClient implements ServiceClient {

    private final OpenApiClient openApiClient;

    public OpenApiServiceClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    @Override
    public Page<String> getServices(String namespaceId, String groupName, int pageNumber, int pageSize) {
        OpenApiRequest request = OpenApiRequest.Builder.create("/v1/ns/service/list")
                .queryParameter("namespaceId", namespaceId)
                .queryParameter("groupName", groupName)
                .queryParameter("pageNo", pageNumber)
                .queryParameter("pageSize", pageSize)
                .build();
        ServiceList serviceList = openApiClient.execute(request, ServiceList.class);
        return new Page<>(pageNumber, pageSize, serviceList.getCount(), serviceList.getDoms());
    }
}
