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
package io.microsphere.nacos.client.v1.server;

import io.microsphere.nacos.client.v1.server.model.ServerSwitch;

/**
 * The Client for Nacos {@link ServerSwitch Server Swtich}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ServerSwitch
 * @since 1.0.0
 */
public interface ServerSwitchClient {

    /**
     * Get the Nacos {@link ServerSwitch Server Swtich}
     *
     * @return non-null
     */
    ServerSwitch getServerSwitch();

    /**
     * Update the Nacos {@link ServerSwitch Server Swtich}
     *
     * @param switchName  the name of {@link ServerSwitch}
     * @param switchValue the value of {@link ServerSwitch}
     * @return <code>true</code> if the {@link ServerSwitch} is updated successfully, otherwise <code>false</code>
     */
    default boolean updateServerSwitch(String switchName, String switchValue) {
        return updateServerSwitch(switchName, switchValue, true);
    }

    /**
     * Update the Nacos Server {@link ServerSwitch}
     *
     * @param switchName  the name of {@link ServerSwitch}
     * @param switchValue the value of {@link ServerSwitch}
     * @param debug       if affect the local server, true means yes, false means no, default true
     * @return <code>true</code> if the {@link ServerSwitch} is updated successfully, otherwise <code>false</code>
     */
    boolean updateServerSwitch(String switchName, String switchValue, boolean debug);
}
