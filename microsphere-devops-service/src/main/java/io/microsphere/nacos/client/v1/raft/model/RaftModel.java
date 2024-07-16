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
package io.microsphere.nacos.client.v1.raft.model;

import io.microsphere.nacos.client.common.model.Model;

/**
 * The {@link Model model} {@link Class} of RAFT
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 * @deprecated will remove in 1.4.x
 */
@Deprecated
public class RaftModel implements Model {

    private static final long serialVersionUID = -1808848379055755464L;

    private RaftPeer leader;

    public RaftPeer getLeader() {
        return leader;
    }

    public void setLeader(RaftPeer leader) {
        this.leader = leader;
    }
}
