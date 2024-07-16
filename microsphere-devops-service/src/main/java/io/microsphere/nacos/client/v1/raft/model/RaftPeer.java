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
 * The model of RAFT Peer
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Model
 * @since 1.0.0
 * @deprecated will remove in 1.4.x
 */
@Deprecated
public class RaftPeer implements Model {

    private static final long serialVersionUID = -5481810936370665179L;

    public String ip;

    public String voteFor;

    public Long term;

    public Long leaderDueMs;

    public Long heartbeatDueMs;

    public State state;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getVoteFor() {
        return voteFor;
    }

    public void setVoteFor(String voteFor) {
        this.voteFor = voteFor;
    }

    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    public Long getLeaderDueMs() {
        return leaderDueMs;
    }

    public void setLeaderDueMs(Long leaderDueMs) {
        this.leaderDueMs = leaderDueMs;
    }

    public Long getHeartbeatDueMs() {
        return heartbeatDueMs;
    }

    public void setHeartbeatDueMs(Long heartbeatDueMs) {
        this.heartbeatDueMs = heartbeatDueMs;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static enum State {
        /**
         * Leader of the cluster, only one leader stands in a cluster.
         */
        LEADER,
        /**
         * Follower of the cluster, report to and copy from leader.
         */
        FOLLOWER,
        /**
         * Candidate leader to be elected.
         */
        CANDIDATE
    }
}
