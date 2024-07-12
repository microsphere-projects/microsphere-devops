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
package io.microsphere.nacos.client.v1.server.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.microsphere.nacos.client.io.GsonDeserializer;
import io.microsphere.nacos.client.v1.server.model.ServerState;

import java.lang.reflect.Type;

/**
 * The {@link GsonDeserializer} class for {@link ServerState}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see GsonDeserializer
 * @see ServerState
 * @since 1.0.0
 */
public class ServerStateDeserializer extends GsonDeserializer<ServerState> {

    private static final String STANDALONE_MODE_FIELD_NAME = "standalone_mode";

    private static final String FUNCTION_MODE_FIELD_NAME = "function_mode";

    private static final String VERSION_FIELD_NAME = "version";

    @Override
    protected ServerState deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String standaloneMode = getString(jsonObject, STANDALONE_MODE_FIELD_NAME);
        String functionMode = getString(jsonObject, FUNCTION_MODE_FIELD_NAME);
        String version = getString(jsonObject, VERSION_FIELD_NAME);
        ServerState serverState = new ServerState();
        serverState.setMode(standaloneMode);
        serverState.setFunctionMode(functionMode);
        serverState.setVersion(version);
        return serverState;
    }

    private String getString(JsonObject jsonObject, String fieldName) {
        JsonElement fieldElement = jsonObject.get(fieldName);
        if (fieldElement == null || fieldElement instanceof JsonNull) {
            return null;
        }
        return fieldElement.getAsString();
    }
}
