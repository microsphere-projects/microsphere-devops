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
package io.microsphere.nacos.client.common.auth;

import io.microsphere.nacos.client.common.auth.model.Authentication;

import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Authorization Manager
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Authentication
 * @since 1.0.0
 */
public class AuthorizationManager implements AutoCloseable {

    private final AuthenticationClient authenticationClient;

    private final ScheduledExecutorService authenticationRefresher;

    /**
     * The latest {@link Authentication} state
     */
    private volatile Authentication authentication;

    public AuthorizationManager(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
        this.authenticationRefresher = newSingleThreadScheduledExecutor(task -> {
            Thread thread = new Thread(task, "Nacos Client - Authorization Refresher");
            thread.setDaemon(true);
            return thread;
        });

        refreshAuthentication();
    }

    private void refreshAuthentication() {
        try {
            this.authentication = this.authenticationClient.authenticate();
        } finally {
            this.authenticationRefresher.schedule(this::refreshAuthentication, getRefreshInterval(getAuthentication()), MILLISECONDS);
        }
    }

    protected Authentication getAuthentication() {
        Authentication authentication = this.authentication;
        return authentication;
    }

    private long getRefreshInterval(Authentication authentication) {
        long tokenTtl = authentication == null ? SECONDS.toMillis(60) : authentication.getTokenTtl();
        return tokenTtl / 2;
    }

    public String getAccessToken() {
        Authentication authentication = getAuthentication();
        return authentication == null ? null : authentication.getAccessToken();
    }

    @Override
    public void close() throws Exception {
        this.authenticationRefresher.shutdown();
    }
}
