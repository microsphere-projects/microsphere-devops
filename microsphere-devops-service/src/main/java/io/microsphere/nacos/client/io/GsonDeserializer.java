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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * The Gson's {@link JsonDeserializer} abstract class provides the template method
 *
 * @param <T> type for which the deserializer is being registered.
 *            It is possible that a deserializer may be asked to deserialize a specific generic type of the T.
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see JsonDeserializer
 * @since 1.0.0
 */
public abstract class GsonDeserializer<T> implements JsonDeserializer<T> {

    @Override
    public final T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        T result = deserialize(json, typeOfT);
        return result == null ? context.deserialize(json, typeOfT) : result;
    }

    /**
     * Deserialize method should be override by the sub-class
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @return a deserialized object of the specified type typeOfT which is a subclass of {@code T}
     * @throws JsonParseException if json is not in the expected format of {@code typeofT}
     */
    protected abstract T deserialize(JsonElement json, Type typeOfT) throws JsonParseException;
}
