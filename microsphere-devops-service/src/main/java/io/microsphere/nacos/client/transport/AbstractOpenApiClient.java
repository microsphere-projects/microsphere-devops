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

import io.microsphere.nacos.client.ErrorCode;
import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.io.DeserializationException;
import io.microsphere.nacos.client.io.Deserializer;

import java.io.Serializable;

import static io.microsphere.nacos.client.ErrorCode.CLIENT_ERROR;
import static io.microsphere.nacos.client.ErrorCode.DESERIALIZATION_ERROR;
import static java.lang.String.format;

/**
 * The Abstract {@link OpenApiClient}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiClient
 * @since 1.0.0
 */
public abstract class AbstractOpenApiClient implements OpenApiClient {

    private final NacosClientConfig nacosClientConfig;

    public AbstractOpenApiClient(NacosClientConfig nacosClientConfig) {
        this.nacosClientConfig = nacosClientConfig;
    }

    @Override
    public <T extends Serializable> T execute(OpenApiRequest request, Class<T> payloadType) throws OpenApiClientException {
        Deserializer deserializer = getDeserializer();
        T payload = null;
        try {
            OpenApiResponse response = execute(request);
            int statusCode = response.getStatusCode();
            if (statusCode == 200) {
                payload = deserializer.deserialize(response.getContent(), payloadType);
            } else {
                ErrorCode errorCode = ErrorCode.valueOf(statusCode);
                String errorMessage = format("The Open API response is valid , status[code : %d , message : %s]",
                        statusCode, response.getStatusMessage());
                throw new OpenApiClientException(errorCode, errorMessage);
            }
        } catch (DeserializationException e) {
            String errorMessage = format("The payload[%s] can't be deserialized", payloadType);
            throw new OpenApiClientException(DESERIALIZATION_ERROR, errorMessage, e);
        } catch (Throwable e) {
            throw new OpenApiClientException(CLIENT_ERROR, e.getMessage(), e);
        }
        return payload;
    }

    /**
     * Get the instance of {@link Deserializer}
     *
     * @return Deserializer
     */
    protected abstract Deserializer getDeserializer();

    public final NacosClientConfig getNacosClientConfig() {
        return nacosClientConfig;
    }
}