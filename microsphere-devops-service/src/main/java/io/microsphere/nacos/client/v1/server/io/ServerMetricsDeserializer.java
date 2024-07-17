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
package io.microsphere.nacos.client.v1.server.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.microsphere.nacos.client.io.GsonDeserializer;
import io.microsphere.nacos.client.v1.server.ServerMetricsClient;
import io.microsphere.nacos.client.v1.server.ServerStatus;
import io.microsphere.nacos.client.v1.server.model.ServerMetrics;

import java.lang.reflect.Type;

/**
 * The {@link GsonDeserializer} class for {@link ServerMetrics}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ServerMetrics
 * @see ServerMetricsClient#getServerMetrics()
 * @see GsonDeserializer
 * @since 1.0.0
 */
public class ServerMetricsDeserializer extends GsonDeserializer<ServerMetrics> {

    private static final String STATUS_MEMBER_NAME = "status";

    private static final String SERVICE_COUNT_MEMBER_NAME = "serviceCount";

    private static final String INSTANCE_COUNT_MEMBER_NAME = "instanceCount";

    private static final String RAFT_NOTIFY_TASK_COUNT_MEMBER_NAME = "raftNotifyTaskCount";

    private static final String RESPONSIBLE_SERVICE_COUNT_MEMBER_NAME = "responsibleServiceCount";

    private static final String RESPONSIBLE_INSTANCE_COUNT_MEMBER_NAME = "responsibleInstanceCount";

    private static final String SYSTEM_CPU_LOAD_MEMBER_NAME = "cpu";

    private static final String SYSTEM_LOAD_AVERAGE_MEMBER_NAME = "load";

    private static final String MEMORY_USAGE_MEMBER_NAME = "mem";

    /**
     * Deserialize an instance of {@link ServerStatus}
     *
     * @param json    The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @return non-null
     * @throws JsonParseException
     */
    @Override
    protected ServerMetrics deserialize(JsonElement json, Type typeOfT) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String status = getString(jsonObject, STATUS_MEMBER_NAME);
        int serviceCount = getInteger(jsonObject, SERVICE_COUNT_MEMBER_NAME);
        int instanceCount = getInteger(jsonObject, INSTANCE_COUNT_MEMBER_NAME);
        int raftNotifyTaskCount = getInteger(jsonObject, RAFT_NOTIFY_TASK_COUNT_MEMBER_NAME);
        int responsibleServiceCount = getInteger(jsonObject, RESPONSIBLE_SERVICE_COUNT_MEMBER_NAME);
        int responsibleInstanceCount = getInteger(jsonObject, RESPONSIBLE_INSTANCE_COUNT_MEMBER_NAME);
        float systemCpuLoad = getFloat(jsonObject, SYSTEM_CPU_LOAD_MEMBER_NAME);
        float systemLoadAverage = getFloat(jsonObject, SYSTEM_LOAD_AVERAGE_MEMBER_NAME);
        float memoryUsage = getFloat(jsonObject, MEMORY_USAGE_MEMBER_NAME);

        ServerMetrics serverMetrics = new ServerMetrics();
        serverMetrics.setServerStatus(ServerStatus.valueOf(status));
        serverMetrics.setServiceCount(serviceCount);
        serverMetrics.setInstanceCount(instanceCount);
        serverMetrics.setRaftNotifyTaskCount(raftNotifyTaskCount);
        serverMetrics.setResponsibleServiceCount(responsibleServiceCount);
        serverMetrics.setResponsibleInstanceCount(responsibleInstanceCount);
        serverMetrics.setSystemCpuLoad(systemCpuLoad);
        serverMetrics.setSystemLoadAverage(systemLoadAverage);
        serverMetrics.setMemoryUsage(memoryUsage);
        return serverMetrics;
    }
}
