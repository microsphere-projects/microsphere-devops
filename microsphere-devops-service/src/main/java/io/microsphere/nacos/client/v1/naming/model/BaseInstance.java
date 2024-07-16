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
 * The abstract {@link Model model} {@link Class} of Service Instance
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public abstract class BaseInstance implements Model {

    private static final long serialVersionUID = -4622496619793842894L;

    /**
     * The ID of namespace (optional)
     * If not specified, the default value is "public"
     */
    private String namespaceId;

    /**
     * The Cluster name (optional)
     */
    private String clusterName;

    /**
     * The Group name (optional)
     * If not specified, the default value is "DEFAULT_GROUP"
     */
    private String groupName;

    /**
     * The Service name (required)
     */
    private String serviceName;

    /**
     * The IP of instance (required)
     */
    private String ip;

    /**
     * The Port of instance (required)
     */
    private int port;

    /**
     * if instance is ephemeral (optional)
     */
    private Boolean ephemeral;

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Boolean getEphemeral() {
        return ephemeral;
    }

    public void setEphemeral(Boolean ephemeral) {
        this.ephemeral = ephemeral;
    }

    @Override
    public String toString() {
        return "{" +
                "namespaceId='" + namespaceId + '\'' +
                ", clusterName='" + clusterName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", ephemeral=" + ephemeral +
                '}';
    }

    /**
     * Copy the properties from the specified {@link BaseInstance}
     *
     * @param that {@link BaseInstance}
     * @return {@link BaseInstance}
     */
    public BaseInstance from(BaseInstance that) {
        this.namespaceId = that.namespaceId;
        this.clusterName = that.clusterName;
        this.groupName = that.groupName;
        this.serviceName = that.serviceName;
        this.ip = that.ip;
        this.port = that.port;
        this.ephemeral = that.ephemeral;
        return this;
    }

}
