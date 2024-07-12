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
package io.microsphere.nacos.client.transport;

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.common.auth.AuthorizationManager;
import io.microsphere.nacos.client.common.auth.OpenApiAuthenticationClient;
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.io.DefaultDeserializer;
import io.microsphere.nacos.client.io.Deserializer;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.ServiceLoader;

import static io.microsphere.nacos.client.ErrorCode.IO_ERROR;

/**
 * {@link OpenApiClient} based on {@link HttpClient}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see HttpClient
 * @since 1.0.0
 */
public class OpenApiHttpClient extends AbstractOpenApiClient {

    private final CloseableHttpClient httpClient;

    private final NacosClientConfig nacosClientConfig;

    private final Deserializer deserializer;

    private final AuthorizationManager authorizationManager;

    public OpenApiHttpClient(NacosClientConfig nacosClientConfig) {
        super(nacosClientConfig);
        int maxConnections = nacosClientConfig.getMaxConnections();
        int maxPerRoute = nacosClientConfig.getMaxPerRoute();
        int connectionTimeout = nacosClientConfig.getConnectionTimeout();
        int readTimeout = nacosClientConfig.getReadTimeout();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

        connectionManager.setMaxTotal(maxConnections);
        connectionManager.setDefaultMaxPerRoute(maxPerRoute);

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectionTimeout)
                .setConnectionRequestTimeout(connectionTimeout)
                .setSocketTimeout(readTimeout)
                .build();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(requestConfig)
                .useSystemProperties();

        this.httpClient = httpClientBuilder.build();
        this.nacosClientConfig = nacosClientConfig;
        this.deserializer = loadDeserializer(nacosClientConfig);
        this.authorizationManager = new AuthorizationManager(new OpenApiAuthenticationClient(this, nacosClientConfig));
    }

    @Override
    protected OpenApiResponse doExecute(OpenApiRequest request) throws OpenApiClientException {
        HttpUriRequest httpUriRequest = buildHttpUriRequest(request);
        CloseableHttpResponse httpResponse;
        OpenApiResponse response = null;
        try {
            httpResponse = httpClient.execute(httpUriRequest);
            response = buildOpenApiResponse(httpResponse);
        } catch (IOException e) {
            throw new OpenApiClientException(IO_ERROR, e.getMessage(), e);
        }
        return response;
    }

    @Override
    protected String getAccessToken() {
        return authorizationManager == null ? null : authorizationManager.getAccessToken();
    }

    @Override
    protected Deserializer getDeserializer() {
        return deserializer;
    }

    private HttpUriRequest buildHttpUriRequest(OpenApiRequest request) {
        HttpMethod method = request.getMethod();
        HttpRequestBase httpRequest = null;
        switch (method) {
            case POST:
                httpRequest = new HttpPost();
                break;
            case PUT:
                httpRequest = new HttpPut();
                break;
            case DELETE:
                httpRequest = new HttpDelete();
                break;
            default:
                httpRequest = new HttpGet();
                break;
        }

        URI uri = buildURI(request);
        httpRequest.setURI(uri);
        return httpRequest;
    }

    private Deserializer loadDeserializer(NacosClientConfig nacosClientConfig) {
        ServiceLoader<Deserializer> serviceLoader = ServiceLoader.load(Deserializer.class);
        // Try to load the first Deserializer by ServiceLoader SPI
        Deserializer firstDeserializer = null;
        for (Deserializer deserializer : serviceLoader) {
            firstDeserializer = deserializer;
            break;
        }
        return firstDeserializer == null ? new DefaultDeserializer(nacosClientConfig) : firstDeserializer;
    }

    private URI buildURI(OpenApiRequest request) {

        StringBuilder urlBuilder = new StringBuilder(128);

        NacosClientConfig config = this.nacosClientConfig;

        String rootPath = buildRootPath(config);

        urlBuilder.append(rootPath).append(request.getEndpoint());

        Map<String, String> queryParameters = request.getQueryParameters();

        if (!queryParameters.isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, String> entry : queryParameters.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                urlBuilder.append(name).append("=").append(value).append("&");
            }
        }

        return URI.create(urlBuilder.toString());
    }

    private String buildRootPath(NacosClientConfig config) {
        String scheme = config.getScheme();

        String prefix = scheme + "://";

        String serverAddress = config.getServerAddress();

        String contextPath = config.getContextPath();

        StringBuilder rootPathBuilder = new StringBuilder(36);

        if (!serverAddress.startsWith(prefix)) {
            rootPathBuilder.append(prefix);
        }

        rootPathBuilder.append(serverAddress);

        rootPathBuilder.append(contextPath);

        return rootPathBuilder.toString();
    }

    private OpenApiResponse buildOpenApiResponse(CloseableHttpResponse httpResponse) throws IOException {
        StatusLine statusLine = httpResponse.getStatusLine();
        HttpEntity httpEntity = httpResponse.getEntity();
        return new OpenApiResponse(statusLine.getStatusCode(), statusLine.getReasonPhrase(), httpEntity.getContent());
    }

    @Override
    public void close() throws Exception {
        this.authorizationManager.close();
        this.httpClient.close();
    }
}
