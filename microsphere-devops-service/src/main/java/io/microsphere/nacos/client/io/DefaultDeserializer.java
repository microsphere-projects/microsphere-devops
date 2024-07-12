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
import com.google.gson.GsonBuilder;
import io.microsphere.nacos.client.NacosClientConfig;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * The default {@link Deserializer} class based on {@link Gson}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Deserializer
 * @see Gson
 * @since 1.0.0
 */
public class DefaultDeserializer implements Deserializer {

    private static final Map<Type, GsonDeserializer> gsonDeserializersCache = loadGsonDeserializers();

    private static Map<Type, GsonDeserializer> loadGsonDeserializers() {
        Map<Type, GsonDeserializer> gsonDeserializersMap = new HashMap<>();
        for (GsonDeserializer gsonDeserializer : ServiceLoader.load(GsonDeserializer.class)) {
            Class<?> gsonDeserializerClass = gsonDeserializer.getClass();
            Type genericSuperclass = gsonDeserializerClass.getGenericSuperclass();
            if (genericSuperclass instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                Type desirializedType = typeArguments[0];
                gsonDeserializersMap.put(desirializedType, gsonDeserializer);
            }
        }
        return gsonDeserializersMap;
    }

    private final Gson gson;

    private final String encoding;

    public DefaultDeserializer(NacosClientConfig nacosClientConfig) {
        this.gson = buildGson();
        this.encoding = nacosClientConfig.getEncoding();
    }

    private Gson buildGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        for (Map.Entry<Type, GsonDeserializer> entry : gsonDeserializersCache.entrySet()) {
            gsonBuilder.registerTypeAdapter(entry.getKey(), entry.getValue());
        }

        return gsonBuilder.create();
    }

    @Override
    public <T extends Serializable> T deserialize(InputStream inputStream, Class<T> deserializedType) throws DeserializationException {
        T object = null;
        try {
            Reader reader = new InputStreamReader(inputStream, encoding);
            object = gson.fromJson(reader, deserializedType);
        } catch (Throwable e) {
            throw new DeserializationException(e.getMessage(), e);
        }
        return object;
    }
}
