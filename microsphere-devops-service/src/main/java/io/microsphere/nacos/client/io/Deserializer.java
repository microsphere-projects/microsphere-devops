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

import io.microsphere.nacos.client.constants.Constants;
import io.microsphere.nacos.client.transport.OpenApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import static io.microsphere.nacos.client.constants.Constants.ENCODING;
import static io.microsphere.nacos.client.util.IOUtils.readAsString;

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
     * Deserialize an object of type T from the given InputStream.
     *
     * @param inputStream      the input stream
     * @param deserializedType
     * @return the deserialized object
     * @throws DeserializationException
     */
    default <T extends Serializable> T deserialize(InputStream inputStream, Class<T> deserializedType) throws DeserializationException {
        String content = null;
        try {
            content = readAsString(inputStream, getEncoding());
        } catch (IOException e) {
            throw new DeserializationException(e.getMessage(), e);
        }
        return deserialize(content, deserializedType);
    }

    /**
     * Deserialize an object of type T from the given InputStream.
     *
     * @param content          the content
     * @param deserializedType the type to be deserialized
     * @return the deserialized object
     * @throws DeserializationException
     */
    <T extends Serializable> T deserialize(String content, Class<T> deserializedType) throws DeserializationException;


    /**
     * The encoding is used for {@link InputStream} when deserializing
     *
     * @return non-null
     * @see Constants#ENCODING
     */
    default String getEncoding() {
        return ENCODING;
    }
}
