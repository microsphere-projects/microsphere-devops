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
package io.microsphere.nacos.client.v1.config.io;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.microsphere.nacos.client.io.GsonDeserializer;
import io.microsphere.nacos.client.v1.config.ConfigClient;
import io.microsphere.nacos.client.v1.config.ConfigType;
import io.microsphere.nacos.client.v1.config.model.Config;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;

/**
 * The {@link GsonDeserializer} for {@link Config}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Config
 * @see ConfigClient#getConfig(String, String, String)
 * @see BaseConfigDeserializer
 * @since 1.0.0
 */
public class ConfigDeserializer extends BaseConfigDeserializer<Config> {

    private static final String ID_MEMBER_NAME = "id";

    private static final String DESCRIPTION_MEMBER_NAME = "desc";

    private static final String USE_MEMBER_NAME = "use";

    private static final String EFFECT_MEMBER_NAME = "effect";

    private static final String SCHEMA_MEMBER_NAME = "schema";

    private static final String TAGS_MEMBER_NAME = "configTags";

    private static final String OPERATOR_IP_MEMBER_NAME = "createUser";

    private static final String OPERATOR_MEMBER_NAME = "createIp";

    private static final String TYPE_MEMBER_NAME = "type";

    private static final String CREATED_TIME_TYPE_MEMBER_NAME = "createTime";

    private static final String LAST_MODIFIED_TIME_TYPE_MEMBER_NAME = "modifyTime";

    @Override
    protected void deserialize(JsonObject jsonObject, Config config, Type typeOfT) throws JsonParseException {
        String id = getString(jsonObject, ID_MEMBER_NAME);
        String description = getString(jsonObject, DESCRIPTION_MEMBER_NAME);
        String use = getString(jsonObject, USE_MEMBER_NAME);
        String effect = getString(jsonObject, EFFECT_MEMBER_NAME);
        String schema = getString(jsonObject, SCHEMA_MEMBER_NAME);
        String tags = getString(jsonObject, TAGS_MEMBER_NAME);
        String type = getString(jsonObject, TYPE_MEMBER_NAME);
        Long createdTime = getLong(jsonObject, CREATED_TIME_TYPE_MEMBER_NAME);
        Long lastModifiedTime = getLong(jsonObject, LAST_MODIFIED_TIME_TYPE_MEMBER_NAME);

        config.setId(id);
        config.setDescription(description);
        config.setUse(use);
        config.setEffect(effect);
        config.setSchema(schema);
        config.setTags(parseTags(tags));
        config.setType(type == null ? null : ConfigType.of(type));
        config.setCreatedTime(createdTime);
        config.setLastModifiedTime(lastModifiedTime);
    }

    @Override
    protected Config newConfig() {
        return new Config();
    }

    @Override
    protected String getOperatorMemberName() {
        return OPERATOR_IP_MEMBER_NAME;
    }

    @Override
    protected String getOperatorIpMemberName() {
        return OPERATOR_MEMBER_NAME;
    }

    private Set<String> parseTags(String tags) {
        if (tags == null) {
            return emptySet();
        }
        String[] tagsArray = tags.split(",");
        return new HashSet<>(asList(tagsArray));
    }
}
