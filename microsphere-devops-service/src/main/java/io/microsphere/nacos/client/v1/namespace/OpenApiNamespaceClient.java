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
package io.microsphere.nacos.client.v1.namespace;

import io.microsphere.nacos.client.common.model.RestResult;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.transport.OpenApiClient;
import io.microsphere.nacos.client.transport.OpenApiClientException;
import io.microsphere.nacos.client.transport.OpenApiRequest;
import io.microsphere.nacos.client.v1.namespace.model.Namespace;

import java.util.List;

/**
 * The {@link NamespaceClient} for <a href="https://nacos.io/en/docs/v1/open-api/#namespace">Open API</a>
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see NamespaceClient
 * @since 1.0.0
 */
public class OpenApiNamespaceClient implements NamespaceClient {

    private static final String NAMESPACES_ENDPOINT = "/v1/console/namespaces";

    private final OpenApiClient openApiClient;

    public OpenApiNamespaceClient(OpenApiClient openApiClient) {
        this.openApiClient = openApiClient;
    }

    @Override
    public List<Namespace> getAllNamespaces() {
        OpenApiRequest request = OpenApiRequest.Builder.create(NAMESPACES_ENDPOINT)
                .build();
        NamespacesList namespacesList = openApiClient.execute(request, NamespacesList.class);
        return namespacesList.getData();
    }

    @Override
    public Namespace getNamespace(String namespaceId) {
        OpenApiRequest request = OpenApiRequest.Builder.create(NAMESPACES_ENDPOINT)
                .queryParameter("namespaceId", namespaceId)
                .queryParameter("show", "all")
                .build();
        Namespace namespace = null;
        try {
            namespace = openApiClient.execute(request, Namespace.class);
        } catch (OpenApiClientException e) {
            // TODO log
        }
        return namespace;
    }

    @Override
    public boolean createNamespace(String namespaceId, String namespaceName, String namespaceDesc) {
        OpenApiRequest request = OpenApiRequest.Builder.create(NAMESPACES_ENDPOINT)
                .method(HttpMethod.POST)
                .queryParameter("customNamespaceId", namespaceId)
                .queryParameter("namespaceName", namespaceName)
                .queryParameter("namespaceDesc", namespaceDesc)
                .build();
        return openApiClient.execute(request, boolean.class);
    }

    @Override
    public boolean updateNamespace(String namespaceId, String namespaceName, String namespaceDesc) {
        OpenApiRequest request = OpenApiRequest.Builder.create(NAMESPACES_ENDPOINT)
                .method(HttpMethod.PUT)
                .queryParameter("namespace", namespaceId)
                .queryParameter("namespaceShowName", namespaceName)
                .queryParameter("namespaceDesc", namespaceDesc)
                .build();
        return openApiClient.execute(request, boolean.class);
    }

    @Override
    public boolean deleteNamespace(String namespaceId) {
        OpenApiRequest request = OpenApiRequest.Builder.create(NAMESPACES_ENDPOINT)
                .method(HttpMethod.DELETE)
                .queryParameter("namespaceId", namespaceId)
                .build();
        return openApiClient.execute(request, boolean.class);
    }

    private static class NamespacesList extends RestResult<List<Namespace>> {
    }
}
