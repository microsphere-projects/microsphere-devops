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

import java.util.List;
import java.util.Map;

/**
 * The {@link Model model} {@link Class} of {@link Instance Instances} List
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class InstancesList implements Model {

    private static final long serialVersionUID = 6369249346713199002L;

    private String dom;

    private Long cacheMillis;

    private Boolean useSpecifiedURL;

    private String name;

    private String checksum;

    private Long lastRefTime;

    private String env;

    private String clusters;

    private List<Instance> hosts;

    private Map<String, String> metadata;

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public Long getCacheMillis() {
        return cacheMillis;
    }

    public void setCacheMillis(Long cacheMillis) {
        this.cacheMillis = cacheMillis;
    }

    public Boolean getUseSpecifiedURL() {
        return useSpecifiedURL;
    }

    public void setUseSpecifiedURL(Boolean useSpecifiedURL) {
        this.useSpecifiedURL = useSpecifiedURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public Long getLastRefTime() {
        return lastRefTime;
    }

    public void setLastRefTime(Long lastRefTime) {
        this.lastRefTime = lastRefTime;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getClusters() {
        return clusters;
    }

    public void setClusters(String clusters) {
        this.clusters = clusters;
    }

    public List<Instance> getHosts() {
        return hosts;
    }

    public void setHosts(List<Instance> hosts) {
        this.hosts = hosts;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
}
