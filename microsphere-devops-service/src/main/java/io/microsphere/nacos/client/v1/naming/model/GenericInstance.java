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
package io.microsphere.nacos.client.v1.naming.model;

import io.microsphere.nacos.client.common.model.Model;

import java.util.Map;

import static io.microsphere.nacos.client.util.JsonUtils.toJSON;

/**
 * The generic {@link Model model} {@link Class} of Service Instance
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see BaseInstance
 * @since 1.0.0
 */
public class GenericInstance extends BaseInstance {

    private static final long serialVersionUID = -6877919356579984749L;

    private Boolean enabled;

    private Double weight;

    private Map<String, String> metadata;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getMetadataAsJSON() {
        return toJSON(this.metadata);
    }

    public GenericInstance from(GenericInstance that) {
        super.from(that);
        this.enabled = that.enabled;
        this.weight = that.weight;
        this.metadata = that.metadata;
        return this;
    }
}
