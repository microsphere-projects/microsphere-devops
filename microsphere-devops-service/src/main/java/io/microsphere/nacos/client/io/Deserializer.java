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
package io.microsphere.nacos.client.io;

import io.microsphere.nacos.client.transport.OpenApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ServiceLoader;

/**
 * The Deserializer interface
 *
 * @param <T> the type of the object to be deserialized
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiClient
 * @since 1.0.0
 */
public interface Deserializer {

    /**
     * The {@link Deserializer} instance
     */
    Deserializer INSTANCE = load();

    /**
     * Load {@link Deserializer} instance
     *
     * @return non-null
     */
    static Deserializer load() {
        ServiceLoader<Deserializer> serviceLoader = ServiceLoader.load(Deserializer.class);
        // Try to load the first Deserializer by ServiceLoader SPI
        Deserializer firstDeserializer = null;
        for (Deserializer deserializer : serviceLoader) {
            firstDeserializer = deserializer;
            break;
        }
        return firstDeserializer == null ? new DefaultDeserializer() : firstDeserializer;
    }

    /**
     * Deserialize an object of type T from the given InputStream.
     *
     * @param inputStream      the input stream
     * @param deserializedType
     * @return the deserialized object
     * @throws IOException
     */
    <T extends Serializable> T deserialize(InputStream inputStream, Class<T> deserializedType) throws IOException;
}
