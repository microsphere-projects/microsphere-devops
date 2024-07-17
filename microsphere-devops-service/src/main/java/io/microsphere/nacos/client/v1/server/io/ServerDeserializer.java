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
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.microsphere.nacos.client.io.GsonDeserializer;
import io.microsphere.nacos.client.v1.server.ServersListClient;
import io.microsphere.nacos.client.v1.server.model.Server;

import java.lang.reflect.Type;

/**
 * The {@link GsonDeserializer} for {@link Server}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Server
 * @see ServersListClient#getServersList()
 * @see GsonDeserializer
 * @since 1.0.0
 */
public class ServerDeserializer extends GsonDeserializer<Server> {

    private static final String IP_MEMBER_NAME = "ip";

    private static final String PORT_MEMBER_NAME = "servePort";

    private static final String SITE_MEMBER_NAME = "site";

    private static final String WEIGHT_MEMBER_NAME = "weight";

    private static final String AD_WEIGHT_MEMBER_NAME = "adWeight";

    private static final String ALIVE_MEMBER_NAME = "alive";

    private static final String LAST_REF_TIME_MEMBER_NAME = "lastRefTime";

    private static final String LAST_REF_TIME_STR_MEMBER_NAME = "lastRefTimeStr";

    private static final String KEY_MEMBER_NAME = "key";

    /**
     * Deserialize an instance of {@link Server}
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @return non-null
     * @throws JsonParseException
     */
    @Override
    protected Server deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String ip = getString(jsonObject, IP_MEMBER_NAME);
        int port = getInteger(jsonObject, PORT_MEMBER_NAME);
        String site = getString(jsonObject, SITE_MEMBER_NAME);
        Float weight = getFloat(jsonObject, WEIGHT_MEMBER_NAME);
        Float adWeight = getFloat(jsonObject, AD_WEIGHT_MEMBER_NAME);
        Boolean alive = getBoolean(jsonObject, ALIVE_MEMBER_NAME);
        Long lastRefTime = getLong(jsonObject, LAST_REF_TIME_MEMBER_NAME);
        String lastRefTimeStr = getString(jsonObject, LAST_REF_TIME_STR_MEMBER_NAME);
        String key = getString(jsonObject, KEY_MEMBER_NAME);

        Server server = new Server();
        server.setIp(ip);
        server.setPort(port);
        server.setSite(site);
        server.setWeight(weight);
        server.setAdWeight(adWeight);
        server.setAlive(alive);
        server.setLastRefTime(lastRefTime);
        server.setLastRefTimeStr(lastRefTimeStr);
        server.setKey(key);
        return server;
    }
}
