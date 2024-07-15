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

import io.microsphere.nacos.client.v1.server.model.Switch;

/**
 * The Client for Nacos Server {@link Switch}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Switch
 * @since 1.0.0
 */
public interface SwitchClient {

    /**
     * Get the Nacos Server {@link Switch}
     *
     * @return non-null
     */
    Switch getSwitch();

    /**
     * Update the Nacos Server {@link Switch}
     *
     * @param switchName  the name of {@link Switch}
     * @param switchValue the value of {@link Switch}
     * @return <code>true</code> if the {@link Switch} is updated successfully, otherwise <code>false</code>
     */
    default boolean updateSwitch(String switchName, String switchValue) {
        return updateSwitch(switchName, switchValue, true);
    }

    /**
     * Update the Nacos Server {@link Switch}
     *
     * @param switchName  the name of {@link Switch}
     * @param switchValue the value of {@link Switch}
     * @param debug       if affect the local server, true means yes, false means no, default true
     * @return <code>true</code> if the {@link Switch} is updated successfully, otherwise <code>false</code>
     */
    boolean updateSwitch(String switchName, String switchValue, boolean debug);
}
