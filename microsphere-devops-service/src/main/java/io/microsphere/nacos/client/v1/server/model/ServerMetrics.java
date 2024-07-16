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
import io.microsphere.nacos.client.v1.server.ServerStatus;

/**
 * The {@link Model model} {@link Class} of Server Metrics
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class ServerMetrics implements Model {

    private static final long serialVersionUID = -5203823870547350315L;

    private ServerStatus serverStatus;

    private Integer serviceCount;

    private Integer instanceCount;

    private Integer raftNotifyTaskCount;

    private Integer responsibleServiceCount;

    private Integer responsibleInstanceCount;

    private Float systemCpuLoad;

    private Float systemLoadAverage;

    private Float memoryUsage;

    public ServerStatus getServerStatus() {
        return serverStatus;
    }

    public void setServerStatus(ServerStatus serverStatus) {
        this.serverStatus = serverStatus;
    }

    public Integer getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
    }

    public Integer getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(Integer instanceCount) {
        this.instanceCount = instanceCount;
    }

    public Integer getRaftNotifyTaskCount() {
        return raftNotifyTaskCount;
    }

    public void setRaftNotifyTaskCount(Integer raftNotifyTaskCount) {
        this.raftNotifyTaskCount = raftNotifyTaskCount;
    }

    public Integer getResponsibleServiceCount() {
        return responsibleServiceCount;
    }

    public void setResponsibleServiceCount(Integer responsibleServiceCount) {
        this.responsibleServiceCount = responsibleServiceCount;
    }

    public Integer getResponsibleInstanceCount() {
        return responsibleInstanceCount;
    }

    public void setResponsibleInstanceCount(Integer responsibleInstanceCount) {
        this.responsibleInstanceCount = responsibleInstanceCount;
    }

    public Float getSystemCpuLoad() {
        return systemCpuLoad;
    }

    public void setSystemCpuLoad(Float systemCpuLoad) {
        this.systemCpuLoad = systemCpuLoad;
    }

    public Float getSystemLoadAverage() {
        return systemLoadAverage;
    }

    public void setSystemLoadAverage(Float systemLoadAverage) {
        this.systemLoadAverage = systemLoadAverage;
    }

    public Float getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(Float memoryUsage) {
        this.memoryUsage = memoryUsage;
    }
}
