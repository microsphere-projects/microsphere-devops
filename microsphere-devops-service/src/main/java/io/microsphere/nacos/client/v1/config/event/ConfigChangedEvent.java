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
package io.microsphere.nacos.client.v1.config.event;

import io.microsphere.nacos.client.constants.Constants;
import io.microsphere.nacos.client.v1.config.model.Config;
import io.microsphere.nacos.client.v1.namespace.model.Namespace;

import java.util.EventObject;

/**
 * The {@link EventObject Event} raised when the Nacos {@link Config} is changed
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EventObject
 * @see Config
 * @since 1.0.0
 */
public class ConfigChangedEvent extends EventObject {

    private final String namespaceId;

    private final String group;

    private final String dataId;

    private final String content;

    /**
     * @param source      the event source
     * @param namespaceId {@link Namespace#getNamespaceId() the id of namespace}, a.k.a the "tenant" (optional).
     *                    if not specified, the {@link Constants#DEFAULT_NAMESPACE_ID "public" namespace} will be used.
     * @param group       the group of {@link Config}
     * @param dataId      the data id of {@link Config}
     * @param content     the content of {@link Config}
     */
    public ConfigChangedEvent(Object source, String namespaceId, String group, String dataId, String content) {
        super(source);
        this.namespaceId = namespaceId;
        this.group = group;
        this.dataId = dataId;
        this.content = content;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public String getGroup() {
        return group;
    }

    public String getDataId() {
        return dataId;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ConfigChangedEvent{" +
                "namespaceId='" + namespaceId + '\'' +
                ", group='" + group + '\'' +
                ", dataId='" + dataId + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public static enum Kind {

        ADDED,

        MODIFIED,

        DELETED
    }
}
