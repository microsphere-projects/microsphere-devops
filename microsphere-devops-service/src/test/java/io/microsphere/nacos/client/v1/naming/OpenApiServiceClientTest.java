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
package io.microsphere.nacos.client.v1.naming;

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.common.model.Page;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.microsphere.nacos.client.v1.naming.ServiceClient.DEFAULT_PAGE_NUMBER;
import static io.microsphere.nacos.client.v1.naming.ServiceClient.DEFAULT_PAGE_SIZE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link OpenApiServiceClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiServiceClient
 * @since 1.0.0
 */
public class OpenApiServiceClientTest extends OpenApiTest {

    @Test
    public void test() {
        OpenApiServiceClient client = new OpenApiServiceClient(openApiClient);
        Page<String> page = client.getServices("test");
        List<String> services = page.getElements();

        assertEquals(DEFAULT_PAGE_NUMBER, page.getPageNumber());
        assertEquals(DEFAULT_PAGE_SIZE, page.getPageSize());
        assertTrue(page.getTotalElements() > DEFAULT_PAGE_SIZE);
        assertEquals(DEFAULT_PAGE_SIZE, page.getNumberOfElements());
        assertEquals(DEFAULT_PAGE_SIZE, services.size());
    }
}
