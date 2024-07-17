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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.microsphere.nacos.client.io.GsonDeserializer;
import io.microsphere.nacos.client.v1.config.model.BaseConfig;

import java.lang.reflect.Type;

/**
 * The abstract {@link GsonDeserializer} class for {@link BaseConfig}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see BaseConfig
 * @see GsonDeserializer
 * @since 1.0.0
 */
public abstract class BaseConfigDeserializer<C extends BaseConfig> extends GsonDeserializer<C> {

    protected static final String NAMESPACE_ID_MEMBER_NAME = "tenant";

    protected static final String GROUP_MEMBER_NAME = "group";

    protected static final String DATA_ID_MEMBER_NAME = "dataId";

    protected static final String APP_NAME_MEMBER_NAME = "appName";

    protected static final String MD5_MEMBER_NAME = "md5";

    protected static final String CONTENT_MEMBER_NAME = "content";

    @Override
    protected final C deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String namespaceId = getString(jsonObject, NAMESPACE_ID_MEMBER_NAME);
        String group = getString(jsonObject, GROUP_MEMBER_NAME);
        String dataId = getString(jsonObject, DATA_ID_MEMBER_NAME);
        String content = getString(jsonObject, CONTENT_MEMBER_NAME);
        String md5 = getString(jsonObject, MD5_MEMBER_NAME);
        String appName = getString(jsonObject, APP_NAME_MEMBER_NAME);
        String operator = getString(jsonObject, getOperatorMemberName());
        String operatorIp = getString(jsonObject, getOperatorIpMemberName());

        C config = newConfig();
        config.setNamespaceId(namespaceId);
        config.setGroup(group);
        config.setDataId(dataId);
        config.setContent(content);
        config.setMd5(md5);
        config.setAppName(appName);
        config.setOperator(operator);
        config.setOperatorIp(operatorIp);

        deserialize(jsonObject, config, typeOfT);
        return config;
    }

    protected abstract void deserialize(JsonObject jsonObject, C config, Type typeOfT) throws JsonParseException;

    protected abstract C newConfig();

    protected abstract String getOperatorMemberName();

    protected abstract String getOperatorIpMemberName();

}
