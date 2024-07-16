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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import static io.microsphere.nacos.client.util.IOUtils.DEFAULT_BUFFER_SIZE;

/**
 * Serializer interface
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Serializable
 * @since 1.0.0
 */
public interface Serializer {

    /**
     * Serialize an object into {@link OutputStream}
     *
     * @param object       an object to be serialized
     * @param outputStream {@link OutputStream}
     * @throws SerializationException if any error occurs
     */
    void serialize(Object object, OutputStream outputStream) throws SerializationException;


    /**
     * Serialize an object as String
     *
     * @param object   an object to be serialized
     * @param encoding the encoding
     * @return String content
     * @throws SerializationException if any error occurs
     */
    default String serializeAsString(Object object, String encoding) throws SerializationException {
        String content = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
            serialize(object, outputStream);
            byte[] bytes = outputStream.toByteArray();
            content = new String(bytes, encoding);
        } catch (IOException e) {
            throw new SerializationException(e.getMessage(), e);
        }
        return content;
    }
}
