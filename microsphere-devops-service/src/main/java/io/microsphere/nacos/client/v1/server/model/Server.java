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
package io.microsphere.nacos.client.v1.server.model;

import io.microsphere.nacos.client.common.model.Model;

/**
 * The {@link Model model} {@link Class} of Nacos Server
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class Server implements Model {

    private static final long serialVersionUID = -3050148488406954752L;

    private String ip;

    private Integer port;

    private String site;

    private Float weight;

    private Float adWeight;

    private Boolean alive;

    private Long lastRefTime;

    private String lastRefTimeStr;

    private String key;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getAdWeight() {
        return adWeight;
    }

    public void setAdWeight(Float adWeight) {
        this.adWeight = adWeight;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Long getLastRefTime() {
        return lastRefTime;
    }

    public void setLastRefTime(Long lastRefTime) {
        this.lastRefTime = lastRefTime;
    }

    public String getLastRefTimeStr() {
        return lastRefTimeStr;
    }

    public void setLastRefTimeStr(String lastRefTimeStr) {
        this.lastRefTimeStr = lastRefTimeStr;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
