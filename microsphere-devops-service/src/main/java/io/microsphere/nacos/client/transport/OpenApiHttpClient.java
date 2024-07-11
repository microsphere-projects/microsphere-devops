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
import io.microsphere.nacos.client.http.HttpMethod;
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

import static io.microsphere.nacos.client.constants.Constants.CONNECTION_TIMEOUT;
import static io.microsphere.nacos.client.constants.Constants.MAX_CONNECTIONS;
import static io.microsphere.nacos.client.constants.Constants.MAX_PER_ROUTE_CONNECTIONS;
import static io.microsphere.nacos.client.constants.Constants.READ_TIMEOUT;

/**
 * {@link OpenApiClient} based on {@link HttpClient}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see HttpClient
 * @since 1.0.0
 */
public class OpenApiHttpClient implements OpenApiClient {

    private final CloseableHttpClient httpClient;

    private final NacosClientConfig nacosClientConfig;

    public OpenApiHttpClient(NacosClientConfig nacosClientConfig) {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE_CONNECTIONS);

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT).setConnectionRequestTimeout(CONNECTION_TIMEOUT).setSocketTimeout(READ_TIMEOUT).build();

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig).useSystemProperties();

        this.httpClient = httpClientBuilder.build();
        this.nacosClientConfig = nacosClientConfig;
    }

    @Override
    public OpenApiResponse execute(OpenApiRequest request) throws OpenApiClientException {
        HttpUriRequest httpUriRequest = buildHttpUriRequest(request);
        CloseableHttpResponse httpResponse;
        OpenApiResponse response = null;
        try {
            httpResponse = httpClient.execute(httpUriRequest);
            response = buildOpenApiResponse(httpResponse);
        } catch (IOException e) {
            throw new OpenApiClientException(e.getMessage(), e);
        }
        return response;
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

    private URI buildURI(OpenApiRequest request) {
        // TODO handle the scheme if duplicated
        StringBuilder urlBuilder = new StringBuilder(256);

        urlBuilder.append(this.nacosClientConfig.getServerAddress()).append(request.getEndpoint());

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

    private OpenApiResponse buildOpenApiResponse(CloseableHttpResponse httpResponse) throws IOException {
        StatusLine statusLine = httpResponse.getStatusLine();
        HttpEntity httpEntity = httpResponse.getEntity();
        return new OpenApiResponse(statusLine.getStatusCode(), statusLine.getReasonPhrase(), httpEntity.getContent());
    }

    @Override
    public void close() throws Exception {
        this.httpClient.close();
    }

}
