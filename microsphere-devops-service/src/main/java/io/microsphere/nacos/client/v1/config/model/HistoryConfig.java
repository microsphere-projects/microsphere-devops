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
package io.microsphere.nacos.client.v1.config.model;

import io.microsphere.nacos.client.common.model.Model;
import io.microsphere.nacos.client.v1.config.ConfigOperationType;

/**
 * The {@link Model model} {@link Class} for Nacos History Configuration
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see GenericConfig
 * @since 1.0.0
 */
public class HistoryConfig extends GenericConfig {

    private static final long serialVersionUID = -3283127326267383094L;

    /**
     * a.k.a "nid"
     */
    private Integer revision;

    /**
     * last "nid"
     */
    private Integer lastRevision;

    private ConfigOperationType operationType;

    public Integer getNid() {
        return getRevision();
    }

    public Integer getRevision() {
        return revision;
    }

    public Integer getLastRevision() {
        return lastRevision;
    }

    public void setLastRevision(Integer lastRevision) {
        this.lastRevision = lastRevision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public ConfigOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(ConfigOperationType operationType) {
        this.operationType = operationType;
    }
}
