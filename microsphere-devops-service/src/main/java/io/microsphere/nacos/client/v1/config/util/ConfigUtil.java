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
package io.microsphere.nacos.client.v1.config.util;

import io.microsphere.nacos.client.constants.Constants;
import io.microsphere.nacos.client.v1.config.model.Config;
import io.microsphere.nacos.client.v1.namespace.model.Namespace;

import static io.microsphere.nacos.client.constants.Constants.LISTENING_CONFIG_FIELD_SEPARATOR_CHAR;

/**
 * The Utility class for Nacos Configuration
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Config
 * @since 1.0.0
 */
public abstract class ConfigUtil {

    /**
     * Generate the data packet of listening {@link Config config}
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}, a.k.a the "tenant" (optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @param contentMD5  the MD5 of {@link Config#getContent() the content}
     * @return dataId^2Group^2contentMD5^2tenant
     */
    public static String buildListeningConfigDataPacket(String namespaceId, String group, String dataId, String contentMD5) {
        return doBuildConfigId(dataId, group, contentMD5, namespaceId);
    }

    /**
     * Generate the id of listening {@link Config config}
     *
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}, a.k.a the "tenant" (optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @return tenant^2Group^2dataId
     */
    public static String buildConfigId(String namespaceId, String group, String dataId) {
        return doBuildConfigId(namespaceId, group, dataId);
    }

    private static String doBuildConfigId(String... fields) {
        StringBuilder idBuilder = new StringBuilder();
        int length = fields.length;
        for (int i = 0; i < length; i++) {
            String field = fields[i];
            if (field != null) {
                idBuilder.append(field);
                if (i < length - 1) {
                    idBuilder.append(LISTENING_CONFIG_FIELD_SEPARATOR_CHAR);
                }
            }
        }

        return idBuilder.toString();
    }
}
