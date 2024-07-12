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
package io.microsphere.nacos.client.v1.auth;

import io.microsphere.nacos.client.NacosClientConfig;
import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.v1.auth.model.Authentication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiAuthenticationClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiAuthenticationClient
 * @since 1.0.0
 */
public class OpenApiAuthenticationClientTest extends OpenApiTest {

    @Test
    public void testAuthenticate() {
        NacosClientConfig nacosClientConfig = this.nacosClientConfig;
        nacosClientConfig.setUsername("nacos");
        nacosClientConfig.setPassword("nacos");
        OpenApiAuthenticationClient client = new OpenApiAuthenticationClient(openApiClient, nacosClientConfig);
        Authentication authentication = client.authenticate();

        assertNotNull(authentication);
        assertNotNull(authentication.getAccessToken());
        assertTrue(authentication.getTokenTtl() > 0);
        assertTrue(authentication.isGlobalAdmin());
    }
}
