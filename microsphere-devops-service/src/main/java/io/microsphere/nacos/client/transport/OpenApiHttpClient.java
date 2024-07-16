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
import io.microsphere.nacos.client.http.HttpMethod;
import io.microsphere.nacos.client.io.DefaultDeserializer;
import io.microsphere.nacos.client.io.DefaultSerializer;
import io.microsphere.nacos.client.io.Deserializer;
import io.microsphere.nacos.client.io.Serializer;
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
import static java.net.URLEncoder.encode;
import static java.util.ServiceLoader.load;

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

    private final Serializer serializer;

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
        this.serializer = loadSerializer(nacosClientConfig);
        this.deserializer = loadDeserializer(nacosClientConfig);
        this.authorizationManager = new AuthorizationManager(this, nacosClientConfig);
    }


    @Override
    protected OpenApiResponse doExecute(OpenApiRequest request) throws OpenApiClientException {
        CloseableHttpResponse httpResponse;
        OpenApiResponse response = null;
        try {
            HttpUriRequest httpUriRequest = buildHttpUriRequest(request);
            httpResponse = this.httpClient.execute(httpUriRequest);
            response = buildOpenApiResponse(httpResponse);
        } catch (IOException e) {
            throw new OpenApiClientException(IO_ERROR, e.getMessage(), e);
        }
        return response;
    }

    @Override
    protected String getAccessToken() {
        AuthorizationManager authorizationManager = this.authorizationManager;
        return authorizationManager == null ? null : authorizationManager.getAccessToken();
    }

    @Override
    public Serializer getSerializer() {
        return this.serializer;
    }

    @Override
    public Deserializer getDeserializer() {
        return this.deserializer;
    }

    private HttpUriRequest buildHttpUriRequest(OpenApiRequest request) throws IOException {
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

    private Serializer loadSerializer(NacosClientConfig nacosClientConfig) {
        Serializer spiSerializer = loadService(Serializer.class);
        return spiSerializer == null ? new DefaultSerializer(nacosClientConfig) : spiSerializer;
    }

    private Deserializer loadDeserializer(NacosClientConfig nacosClientConfig) {
        Deserializer spiDeserializer = loadService(Deserializer.class);
        return spiDeserializer == null ? new DefaultDeserializer(nacosClientConfig) : spiDeserializer;
    }

    private <S> S loadService(Class<S> serviceClass) {
        ServiceLoader<S> serviceLoader = load(serviceClass);
        // Try to load the first SPI by ServiceLoader SPI
        S firstService = null;
        for (S spi : serviceLoader) {
            firstService = spi;
            break;
        }
        return firstService;
    }

    private URI buildURI(OpenApiRequest request) throws IOException {

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
                urlBuilder.append(name).append("=");
                if (value != null) {
                    String encodedValue = encode(value, config.getEncoding());
                    urlBuilder.append(encodedValue);
                }
                urlBuilder.append("&");
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
