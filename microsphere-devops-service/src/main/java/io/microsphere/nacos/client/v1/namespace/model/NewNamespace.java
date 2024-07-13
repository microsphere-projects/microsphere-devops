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
package io.microsphere.nacos.client.v1.namespace.model;

import io.microsphere.nacos.client.common.model.Model;

/**
 * The model class for creating a new {@link Namespace namespace}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Namespace
 * @since 1.0.0
 */
public class NewNamespace implements Model {

    private static final long serialVersionUID = -9178229297781965872L;

    private final String namespaceId;

    private final String namespaceName;

    private final String namespaceDesc;

    public NewNamespace(String namespaceId, String namespaceName) {
        this(namespaceId, namespaceName, null);
    }

    public NewNamespace(String namespaceId, String namespaceName, String namespaceDesc) {
        this.namespaceId = namespaceId;
        this.namespaceName = namespaceName;
        this.namespaceDesc = namespaceDesc;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public String getNamespaceDesc() {
        return namespaceDesc;
    }
}
