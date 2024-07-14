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
import java.util.Objects;

/**
 * The model of Instance
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class Instance implements Model {


    private static final long serialVersionUID = -605793020763891332L;

    private String namespaceId;

    private String groupName;

    private String instanceId;

    private String serviceName;

    private String service;

    private int port;

    private boolean healthy;

    private String ip;

    private String clusterName;

    private float weight;

    private boolean enabled;

    private boolean valid;

    private boolean marked;

    private boolean ephemeral;

    private Map<String, String> metadata;

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getServiceName() {
        return serviceName == null ? getService() : serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
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

    public void setService(String service) {
        this.service = service;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public boolean isEphemeral() {
        return ephemeral;
    }

    public void setEphemeral(boolean ephemeral) {
        this.ephemeral = ephemeral;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instance instance)) return false;
        return port == instance.port && healthy == instance.healthy && Float.compare(weight, instance.weight) == 0
                && enabled == instance.enabled && valid == instance.valid && marked == instance.marked
                && ephemeral == instance.ephemeral && Objects.equals(namespaceId, instance.namespaceId)
                && Objects.equals(groupName, instance.groupName) && Objects.equals(instanceId, instance.instanceId)
                && Objects.equals(serviceName, instance.serviceName) && Objects.equals(service, instance.service)
                && Objects.equals(ip, instance.ip) && Objects.equals(clusterName, instance.clusterName)
                && Objects.equals(metadata, instance.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespaceId, groupName, instanceId, serviceName, service, port, healthy, ip, clusterName,
                weight, enabled, valid, marked, ephemeral, metadata);
    }

    @Override
    public String toString() {
        return "Instance{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", instanceId='" + instanceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", service='" + service + '\'' +
                ", port=" + port +
                ", healthy=" + healthy +
                ", ip='" + ip + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", weight=" + weight +
                ", enabled=" + enabled +
                ", valid=" + valid +
                ", marked=" + marked +
                ", ephemeral=" + ephemeral +
                ", metadata=" + metadata +
                '}';
    }
}
