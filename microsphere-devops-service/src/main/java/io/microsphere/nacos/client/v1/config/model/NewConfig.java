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
import io.microsphere.nacos.client.v1.config.ConfigType;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

/**
 * The {@link Model model} {@link Class} for Nacos Configuration to be published
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see BaseConfig
 * @since 1.0.0
 */
public class NewConfig extends BaseConfig {

    private static final long serialVersionUID = 7120432352020094941L;

    private String description;

    private Set<String> tags;

    private String use;

    private String effect;

    private ConfigType type;

    private String schema;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public void addTags(String oneTag, String... anotherTags) {
        Set<String> tags = this.tags;
        if (tags == null) {
            tags = new HashSet<>(1 + anotherTags.length);
            this.tags = tags;
        }
        tags.add(oneTag);
        tags.addAll(asList(anotherTags));
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public ConfigType getType() {
        return type;
    }

    public void setType(ConfigType type) {
        this.type = type;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
}
