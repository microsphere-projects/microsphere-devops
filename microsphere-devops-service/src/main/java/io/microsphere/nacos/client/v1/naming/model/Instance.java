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

/**
 * The {@link Model model} {@link Class} of Service Instance
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class Instance extends NewInstance {

    private static final long serialVersionUID = -6481881580931739934L;

    private String instanceId;

    private String service;

    private Boolean valid;

    private Boolean marked;

    private String instanceIdGenerator;

    private Long instanceHeartBeatInterval;

    private Long instanceHeartBeatTimeOut;

    private Long ipDeleteTimeout;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getService() {
        String service = this.service;
        // FIXME : There is different between Open-API Doc and actual response
        if (service != null) {
            int index = service.indexOf("@@");
            service = index > -1 ? service.substring(index + 2) : service;
        }
        return service;
    }

    @Override
    public String getServiceName() {
        String serviceName = super.getServiceName();
        return serviceName == null ? getService() : serviceName;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getMarked() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked = marked;
    }

    public String getInstanceIdGenerator() {
        return instanceIdGenerator;
    }

    public void setInstanceIdGenerator(String instanceIdGenerator) {
        this.instanceIdGenerator = instanceIdGenerator;
    }

    public Long getInstanceHeartBeatInterval() {
        return instanceHeartBeatInterval;
    }

    public void setInstanceHeartBeatInterval(Long instanceHeartBeatInterval) {
        this.instanceHeartBeatInterval = instanceHeartBeatInterval;
    }

    public Long getInstanceHeartBeatTimeOut() {
        return instanceHeartBeatTimeOut;
    }

    public void setInstanceHeartBeatTimeOut(Long instanceHeartBeatTimeOut) {
        this.instanceHeartBeatTimeOut = instanceHeartBeatTimeOut;
    }

    public Long getIpDeleteTimeout() {
        return ipDeleteTimeout;
    }

    public void setIpDeleteTimeout(Long ipDeleteTimeout) {
        this.ipDeleteTimeout = ipDeleteTimeout;
    }

    public Instance from(Instance that) {
        super.from(that);
        this.instanceId = that.instanceId;
        this.service = that.service;
        this.valid = that.valid;
        this.marked = that.marked;
        this.instanceIdGenerator = that.instanceIdGenerator;
        this.instanceHeartBeatInterval = that.instanceHeartBeatInterval;
        this.instanceHeartBeatTimeOut = that.instanceHeartBeatTimeOut;
        this.ipDeleteTimeout = that.ipDeleteTimeout;
        return this;
    }
}
