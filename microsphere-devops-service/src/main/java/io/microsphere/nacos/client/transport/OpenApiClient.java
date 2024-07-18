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
import io.microsphere.nacos.client.io.Serializer;

import java.lang.reflect.Type;

/**
 * The Nacos Client for Open API
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiRequest
 * @see OpenApiRequestParam
 * @see OpenApiResponse
 * @see Serializer
 * @see Deserializer
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
    <T> T execute(OpenApiRequest request, Type payloadType) throws OpenApiClientException;

    /**
     * Get the instance of {@link Serializer}
     *
     * @return non-null
     */
    Serializer getSerializer();

    /**
     * Get the instance of {@link Deserializer}
     *
     * @return non-null
     */
    Deserializer getDeserializer();

    /**
     * Get the encoding
     *
     * @return non-null
     */
    String getEncoding();
}
