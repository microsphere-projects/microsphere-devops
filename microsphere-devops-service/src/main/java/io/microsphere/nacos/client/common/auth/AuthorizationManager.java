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

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.common.auth.model.Authentication;
import io.microsphere.nacos.client.transport.OpenApiClient;

import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
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

    private final NacosClientConfig nacosClientConfig;

    private final ScheduledExecutorService authenticationRefresher;

    /**
     * The latest {@link Authentication} state
     */
    private volatile Authentication authentication;

    public AuthorizationManager(OpenApiClient openApiClient, NacosClientConfig nacosClientConfig) {
        this.authenticationClient = new OpenApiAuthenticationClient(openApiClient, nacosClientConfig);
        this.nacosClientConfig = nacosClientConfig;
        this.authenticationRefresher = initAuthenticationRefresher();
        refreshAuthentication();
    }

    private boolean isAuthorizationEnabled() {
        return this.nacosClientConfig.isAuthorizationEnabled();
    }

    private ScheduledExecutorService initAuthenticationRefresher() {
        if (isAuthorizationEnabled()) {
            return newSingleThreadScheduledExecutor(task -> {
                Thread thread = new Thread(task, "Nacos Client - Authorization Refresher");
                thread.setDaemon(true);
                return thread;
            });
        }
        return null;
    }

    private void refreshAuthentication() throws AuthorizationException {
        if (isAuthorizationEnabled()) {
            try {
                this.authentication = this.authenticationClient.authenticate();
            } catch (Throwable e) {
                String errorMessage = "Failed to refresh Nacos Client Authentication";
                throw new AuthorizationException(errorMessage, e);
            } finally {
                scheduleRefresh();
            }
        }
    }

    private void scheduleRefresh() {
        this.authenticationRefresher.schedule(this::refreshAuthentication, getRefreshIntervalInSeconds(getAuthentication()), SECONDS);
    }

    protected Authentication getAuthentication() {
        return this.isAuthorizationEnabled() ? this.authentication : null;
    }

    private long getRefreshIntervalInSeconds(Authentication authentication) {
        long tokenTtlInSeconds = authentication == null ? 60 : authentication.getTokenTtl();
        return tokenTtlInSeconds / 2;
    }

    public String getAccessToken() {
        Authentication authentication = getAuthentication();
        return authentication == null ? null : authentication.getAccessToken();
    }

    @Override
    public void close() throws Exception {
        ScheduledExecutorService authenticationRefresher = this.authenticationRefresher;
        if (authenticationRefresher != null) {
            this.authenticationRefresher.shutdown();
        }
    }
}
