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
import io.microsphere.nacos.client.v1.config.ConfigClient;
import io.microsphere.nacos.client.v1.config.ConfigOperationType;
import io.microsphere.nacos.client.v1.config.model.HistoryConfig;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The {@link GsonDeserializer} class for {@link HistoryConfig}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see HistoryConfig
 * @see HistoryConfigPageDeserializer
 * @see ConfigClient#getHistoryConfigs(String, String, String, int, int)
 * @since 1.0.0
 */
public class HistoryConfigDeserializer extends GsonDeserializer<HistoryConfig> {

    private static final String NAMESPACE_ID_MEMBER_NAME = "tenant";

    private static final String GROUP_MEMBER_NAME = "group";

    private static final String DATA_ID_MEMBER_NAME = "dataId";

    private static final String REVISION_MEMBER_NAME = "id";

    private static final String LAST_REVISION_MEMBER_NAME = "lastId";

    private static final String APP_NAME_MEMBER_NAME = "appName";

    private static final String MD5_MEMBER_NAME = "md5";

    private static final String CONTENT_MEMBER_NAME = "content";

    private static final String OPERATOR_IP_MEMBER_NAME = "srcIp";

    private static final String OPERATOR_MEMBER_NAME = "srcUser";

    private static final String CONFIG_OPERATION_TYPE_MEMBER_NAME = "opType";

    private static final String CREATED_TIME_TYPE_MEMBER_NAME = "createdTime";

    private static final String LAST_MODIFIED_TIME_TYPE_MEMBER_NAME = "lastModifiedTime";

    /**
     * e.g : 2010-05-05T00:00:00.000+08:00
     */
    private static final String DATE_FORMAT = "YYYY-MM-DD'T'HH:mm:ss.SSSXXX";

    @Override
    protected HistoryConfig deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String namespaceId = getString(jsonObject, NAMESPACE_ID_MEMBER_NAME);
        String group = getString(jsonObject, GROUP_MEMBER_NAME);
        String dataId = getString(jsonObject, DATA_ID_MEMBER_NAME);
        String content = getString(jsonObject, CONTENT_MEMBER_NAME);
        String revision = getString(jsonObject, REVISION_MEMBER_NAME);
        Long lastRevision = getLong(jsonObject, LAST_REVISION_MEMBER_NAME);
        String appName = getString(jsonObject, APP_NAME_MEMBER_NAME);
        String md5 = getString(jsonObject, MD5_MEMBER_NAME);
        String operatorIp = getString(jsonObject, OPERATOR_IP_MEMBER_NAME);
        String operator = getString(jsonObject, OPERATOR_MEMBER_NAME);
        String operationType = getString(jsonObject, CONFIG_OPERATION_TYPE_MEMBER_NAME);
        String createdTime = getString(jsonObject, CREATED_TIME_TYPE_MEMBER_NAME);
        String lastModifiedTime = getString(jsonObject, LAST_MODIFIED_TIME_TYPE_MEMBER_NAME);

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        HistoryConfig historyConfig = new HistoryConfig();
        historyConfig.setNamespaceId(namespaceId);
        historyConfig.setGroup(group);
        historyConfig.setDataId(dataId);
        historyConfig.setContent(content);
        historyConfig.setRevision(Long.parseLong(revision));
        historyConfig.setLastRevision(lastRevision);
        historyConfig.setAppName(appName);
        historyConfig.setMd5(md5);
        historyConfig.setOperatorIp(operatorIp);
        historyConfig.setOperator(operator);
        historyConfig.setOperationType(ConfigOperationType.of(operationType));
        try {
            historyConfig.setCreatedTime(dateFormat.parse(createdTime).getTime());
            historyConfig.setLastModifiedTime(dateFormat.parse(lastModifiedTime).getTime());
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
        return historyConfig;
    }
}
