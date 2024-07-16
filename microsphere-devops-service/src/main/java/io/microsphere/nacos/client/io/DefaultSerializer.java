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

import com.google.gson.Gson;
import io.microsphere.nacos.client.NacosClientConfig;

import java.io.OutputStream;

/**
 * The default {@link Serializer} based on {@link Gson}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Serializer
 * @since 1.0.0
 */
public class DefaultSerializer implements Serializer {

    private final Gson gson;

    private final String encoding;

    public DefaultSerializer(NacosClientConfig nacosClientConfig) {
        this.encoding = nacosClientConfig.getEncoding();
        this.gson = buildGson();
    }

    private Gson buildGson() {
        // TODO support the more customized implementations
        return new Gson();
    }

    @Override
    public void serialize(Object object, OutputStream outputStream) throws SerializationException {
        try {
            String json = this.gson.toJson(object);
            byte[] bytes = json.getBytes(this.encoding);
            outputStream.write(bytes);
        } catch (Throwable e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }
}
