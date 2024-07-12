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

import io.microsphere.nacos.client.io.Deserializer;

import java.io.IOException;
import java.io.Serializable;

import static java.lang.String.format;

/**
 * The Nacos Client for Open API
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see AutoCloseable
 * @since 1.0.0
 */
public interface OpenApiClient extends AutoCloseable {

    /**
     * Execute the {@link OpenApiRequest}
     *
     * @param request the {@link OpenApiRequest}
     * @return the {@link OpenApiResponse}
     * @throws OpenApiClientException
     */
    OpenApiResponse execute(OpenApiRequest request) throws OpenApiClientException;

    /**
     * Execute the {@link OpenApiRequest}
     *
     * @param request     the {@link OpenApiRequest}
     * @param payloadType the class of payload body
     * @param <T>         the class of payload body
     * @return the payload instance
     * @throws OpenApiClientException
     */
    default <T extends Serializable> T execute(OpenApiRequest request, Class<T> payloadType) throws OpenApiClientException {
        OpenApiResponse response = null;
        Deserializer deserializer = Deserializer.INSTANCE;
        T payload = null;
        try {
            response = execute(request);
            int statusCode = response.getStatusCode();
            if (statusCode == 200) {
                payload = deserializer.deserialize(response.getContent(), payloadType);
            } else {
                throw new OpenApiClientException(format("The Open API response is valid , status[code : %d , message : %s]",
                        statusCode, response.getStatusMessage()));
            }
        } catch (IOException e) {
            throw new OpenApiClientException(format("The payload[%s] can't be deserialized", payloadType), e);
        } catch (Throwable e) {
            throw new OpenApiClientException(e.getMessage(), e);
        }
        return payload;
    }

}