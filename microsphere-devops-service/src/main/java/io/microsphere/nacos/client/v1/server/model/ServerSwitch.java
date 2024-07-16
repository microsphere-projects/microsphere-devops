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

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The model of Nacos Server Switch
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 */
public class ServerSwitch implements Model {

    private static final long serialVersionUID = -2905800184770325864L;

    private String name;

    private String checksum;

    private List<String> masters;

    private Map<String, Integer> adWeightMap;

    private Long defaultPushCacheMillis;

    private Long clientBeatInterval;

    private Long defaultCacheMillis;

    private Float distroThreshold;

    private Boolean healthCheckEnabled;

    private Boolean autoChangeHealthCheckEnabled;

    private Boolean distroEnabled;

    private Boolean enableStandalone;

    private Boolean pushEnabled;

    private Integer checkTimes;

    private HealthParams httpHealthParams;

    private HealthParams tcpHealthParams;

    private HealthParams mysqlHealthParams;

    private List<String> incrementalList;

    private Long serverStatusSynchronizationPeriodMillis;

    private Long serviceStatusSynchronizationPeriodMillis;

    private Boolean disableAddIP;

    private Boolean sendBeatOnly;

    private Boolean lightBeatEnabled;

    private Map<String, Integer> limitedUrlMap;

    /**
     * The server is regarded as expired if its two reporting interval is lagger than this variable.
     */
    private Long distroServerExpiredMillis;

    /**
     * since which version, push can be enabled.
     */
    private String pushGoVersion;

    private String pushJavaVersion;

    private String pushPythonVersion;

    private String pushCVersion;

    private String pushCSharpVersion;

    private Boolean enableAuthentication;

    private String overriddenServerStatus;

    private Boolean defaultInstanceEphemeral;

    private Set<String> healthCheckWhiteList;

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

    public List<String> getMasters() {
        return masters;
    }

    public void setMasters(List<String> masters) {
        this.masters = masters;
    }

    public Map<String, Integer> getAdWeightMap() {
        return adWeightMap;
    }

    public void setAdWeightMap(Map<String, Integer> adWeightMap) {
        this.adWeightMap = adWeightMap;
    }

    public Long getDefaultPushCacheMillis() {
        return defaultPushCacheMillis;
    }

    public void setDefaultPushCacheMillis(Long defaultPushCacheMillis) {
        this.defaultPushCacheMillis = defaultPushCacheMillis;
    }

    public Long getClientBeatInterval() {
        return clientBeatInterval;
    }

    public void setClientBeatInterval(Long clientBeatInterval) {
        this.clientBeatInterval = clientBeatInterval;
    }

    public Long getDefaultCacheMillis() {
        return defaultCacheMillis;
    }

    public void setDefaultCacheMillis(Long defaultCacheMillis) {
        this.defaultCacheMillis = defaultCacheMillis;
    }

    public Float getDistroThreshold() {
        return distroThreshold;
    }

    public void setDistroThreshold(Float distroThreshold) {
        this.distroThreshold = distroThreshold;
    }

    public Boolean getHealthCheckEnabled() {
        return healthCheckEnabled;
    }

    public void setHealthCheckEnabled(Boolean healthCheckEnabled) {
        this.healthCheckEnabled = healthCheckEnabled;
    }

    public Boolean getAutoChangeHealthCheckEnabled() {
        return autoChangeHealthCheckEnabled;
    }

    public void setAutoChangeHealthCheckEnabled(Boolean autoChangeHealthCheckEnabled) {
        this.autoChangeHealthCheckEnabled = autoChangeHealthCheckEnabled;
    }

    public Boolean getDistroEnabled() {
        return distroEnabled;
    }

    public void setDistroEnabled(Boolean distroEnabled) {
        this.distroEnabled = distroEnabled;
    }

    public Boolean getEnableStandalone() {
        return enableStandalone;
    }

    public void setEnableStandalone(Boolean enableStandalone) {
        this.enableStandalone = enableStandalone;
    }

    public Boolean getPushEnabled() {
        return pushEnabled;
    }

    public void setPushEnabled(Boolean pushEnabled) {
        this.pushEnabled = pushEnabled;
    }

    public Integer getCheckTimes() {
        return checkTimes;
    }

    public void setCheckTimes(Integer checkTimes) {
        this.checkTimes = checkTimes;
    }

    public HealthParams getHttpHealthParams() {
        return httpHealthParams;
    }

    public void setHttpHealthParams(HealthParams httpHealthParams) {
        this.httpHealthParams = httpHealthParams;
    }

    public HealthParams getTcpHealthParams() {
        return tcpHealthParams;
    }

    public void setTcpHealthParams(HealthParams tcpHealthParams) {
        this.tcpHealthParams = tcpHealthParams;
    }

    public HealthParams getMysqlHealthParams() {
        return mysqlHealthParams;
    }

    public void setMysqlHealthParams(HealthParams mysqlHealthParams) {
        this.mysqlHealthParams = mysqlHealthParams;
    }

    public List<String> getIncrementalList() {
        return incrementalList;
    }

    public void setIncrementalList(List<String> incrementalList) {
        this.incrementalList = incrementalList;
    }

    public Long getServerStatusSynchronizationPeriodMillis() {
        return serverStatusSynchronizationPeriodMillis;
    }

    public void setServerStatusSynchronizationPeriodMillis(Long serverStatusSynchronizationPeriodMillis) {
        this.serverStatusSynchronizationPeriodMillis = serverStatusSynchronizationPeriodMillis;
    }

    public Long getServiceStatusSynchronizationPeriodMillis() {
        return serviceStatusSynchronizationPeriodMillis;
    }

    public void setServiceStatusSynchronizationPeriodMillis(Long serviceStatusSynchronizationPeriodMillis) {
        this.serviceStatusSynchronizationPeriodMillis = serviceStatusSynchronizationPeriodMillis;
    }

    public Boolean getDisableAddIP() {
        return disableAddIP;
    }

    public void setDisableAddIP(Boolean disableAddIP) {
        this.disableAddIP = disableAddIP;
    }

    public Boolean getSendBeatOnly() {
        return sendBeatOnly;
    }

    public void setSendBeatOnly(Boolean sendBeatOnly) {
        this.sendBeatOnly = sendBeatOnly;
    }

    public Boolean getLightBeatEnabled() {
        return lightBeatEnabled;
    }

    public void setLightBeatEnabled(Boolean lightBeatEnabled) {
        this.lightBeatEnabled = lightBeatEnabled;
    }

    public Map<String, Integer> getLimitedUrlMap() {
        return limitedUrlMap;
    }

    public void setLimitedUrlMap(Map<String, Integer> limitedUrlMap) {
        this.limitedUrlMap = limitedUrlMap;
    }

    public Long getDistroServerExpiredMillis() {
        return distroServerExpiredMillis;
    }

    public void setDistroServerExpiredMillis(Long distroServerExpiredMillis) {
        this.distroServerExpiredMillis = distroServerExpiredMillis;
    }

    public String getPushGoVersion() {
        return pushGoVersion;
    }

    public void setPushGoVersion(String pushGoVersion) {
        this.pushGoVersion = pushGoVersion;
    }

    public String getPushJavaVersion() {
        return pushJavaVersion;
    }

    public void setPushJavaVersion(String pushJavaVersion) {
        this.pushJavaVersion = pushJavaVersion;
    }

    public String getPushPythonVersion() {
        return pushPythonVersion;
    }

    public void setPushPythonVersion(String pushPythonVersion) {
        this.pushPythonVersion = pushPythonVersion;
    }

    public String getPushCVersion() {
        return pushCVersion;
    }

    public void setPushCVersion(String pushCVersion) {
        this.pushCVersion = pushCVersion;
    }

    public String getPushCSharpVersion() {
        return pushCSharpVersion;
    }

    public void setPushCSharpVersion(String pushCSharpVersion) {
        this.pushCSharpVersion = pushCSharpVersion;
    }

    public Boolean getEnableAuthentication() {
        return enableAuthentication;
    }

    public void setEnableAuthentication(Boolean enableAuthentication) {
        this.enableAuthentication = enableAuthentication;
    }

    public String getOverriddenServerStatus() {
        return overriddenServerStatus;
    }

    public void setOverriddenServerStatus(String overriddenServerStatus) {
        this.overriddenServerStatus = overriddenServerStatus;
    }

    public Boolean getDefaultInstanceEphemeral() {
        return defaultInstanceEphemeral;
    }

    public void setDefaultInstanceEphemeral(Boolean defaultInstanceEphemeral) {
        this.defaultInstanceEphemeral = defaultInstanceEphemeral;
    }

    public Set<String> getHealthCheckWhiteList() {
        return healthCheckWhiteList;
    }

    public void setHealthCheckWhiteList(Set<String> healthCheckWhiteList) {
        this.healthCheckWhiteList = healthCheckWhiteList;
    }

    @Override
    public String toString() {
        return "Switch{" +
                "name='" + name + '\'' +
                ", checksum='" + checksum + '\'' +
                ", masters=" + masters +
                ", adWeightMap=" + adWeightMap +
                ", defaultPushCacheMillis=" + defaultPushCacheMillis +
                ", clientBeatInterval=" + clientBeatInterval +
                ", defaultCacheMillis=" + defaultCacheMillis +
                ", distroThreshold=" + distroThreshold +
                ", healthCheckEnabled=" + healthCheckEnabled +
                ", autoChangeHealthCheckEnabled=" + autoChangeHealthCheckEnabled +
                ", distroEnabled=" + distroEnabled +
                ", enableStandalone=" + enableStandalone +
                ", pushEnabled=" + pushEnabled +
                ", checkTimes=" + checkTimes +
                ", httpHealthParams=" + httpHealthParams +
                ", tcpHealthParams=" + tcpHealthParams +
                ", mysqlHealthParams=" + mysqlHealthParams +
                ", incrementalList=" + incrementalList +
                ", serverStatusSynchronizationPeriodMillis=" + serverStatusSynchronizationPeriodMillis +
                ", serviceStatusSynchronizationPeriodMillis=" + serviceStatusSynchronizationPeriodMillis +
                ", disableAddIP=" + disableAddIP +
                ", sendBeatOnly=" + sendBeatOnly +
                ", lightBeatEnabled=" + lightBeatEnabled +
                ", limitedUrlMap=" + limitedUrlMap +
                ", distroServerExpiredMillis=" + distroServerExpiredMillis +
                ", pushGoVersion='" + pushGoVersion + '\'' +
                ", pushJavaVersion='" + pushJavaVersion + '\'' +
                ", pushPythonVersion='" + pushPythonVersion + '\'' +
                ", pushCVersion='" + pushCVersion + '\'' +
                ", pushCSharpVersion='" + pushCSharpVersion + '\'' +
                ", enableAuthentication=" + enableAuthentication +
                ", overriddenServerStatus='" + overriddenServerStatus + '\'' +
                ", defaultInstanceEphemeral=" + defaultInstanceEphemeral +
                ", healthCheckWhiteList=" + healthCheckWhiteList +
                '}';
    }

    public static class HealthParams {

        /**
         * Maximum RT
         */
        private Integer max;

        /**
         * Minimum RT
         */
        private Integer min;

        /**
         * Get Factor to reevaluate RT
         */
        private Float factor;

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Float getFactor() {
            return factor;
        }

        public void setFactor(Float factor) {
            this.factor = factor;
        }
    }
}
