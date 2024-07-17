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
package io.microsphere.nacos.client.v1.config;

import io.microsphere.nacos.client.OpenApiTest;
import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.v1.config.model.Config;
import io.microsphere.nacos.client.v1.config.model.GenericConfig;
import io.microsphere.nacos.client.v1.config.model.HistoryConfig;
import io.microsphere.nacos.client.v1.config.model.NewConfig;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link ConfigClient} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see ConfigClient
 * @since 1.0.0
 */
public class ConfigClientTest extends OpenApiTest {

    private static final String TEST_DATA_ID = "test-config";

    private static final String CONTENT = "Test Config Content...";

    private static final String TAG = "test-tag";

    private static final Set<String> TAGS = new HashSet<>(asList("test-tag-1", "test-tag-2", "test-tag-3"));

    private static final String APP_NAME = "test-app";

    private static final String DESCRIPTION = "This is a description for test-config";

    private static final String OPERATOR = "microsphere-nacos-client";

    private static final String USE = "test";

    private static final String EFFECT = "Effect 1";

    private static final String SCHEMA = "test config schema";

    private static final ConfigType CONFIG_TYPE = ConfigType.TEXT;

    @Test
    public void test() {
        ConfigClient client = new OpenApiConfigClient(openApiClient);

        // test publishConfig() to create a new config
        NewConfig newConfig = createNewConfig();
        assertTrue(client.publishConfig(newConfig));

        // test publishConfigContent() to update the content
        assertTrue(client.publishConfigContent(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID, "Update Content"));

        // test getHistoryConfigs()
        Page<HistoryConfig> page = client.getHistoryConfigs(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID);
        assertTrue(page.isFirst());
        assertEquals(1, page.getPageNumber());
        assertEquals(ConfigClient.DEFAULT_PAGE_SIZE, page.getPageSize());
        List<HistoryConfig> historyConfigs = page.getElements();
        assertTrue(historyConfigs.size() > 1);
        HistoryConfig historyConfig = historyConfigs.get(0);
        assertHistoryConfig(historyConfig);

        // test getHistoryConfig();
        HistoryConfig historyConfig1 = client.getHistoryConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID, historyConfig.getRevision());
        assertHistoryConfig(historyConfig1);
        assertEquals(historyConfig.getRevision(), historyConfig1.getRevision());
        assertEquals(historyConfig.getNid(), historyConfig1.getNid());
        assertEquals(historyConfig.getLastRevision(), historyConfig1.getLastRevision());
        assertEquals(historyConfig.getDescription(), historyConfig1.getDescription());
        assertEquals(historyConfig.getOperator(), historyConfig1.getOperator());
        assertEquals(historyConfig.getOperatorIp(), historyConfig1.getOperatorIp());
        assertEquals(historyConfig.getOperationType(), historyConfig1.getOperationType());
        assertEquals(historyConfig.getCreatedTime(), historyConfig1.getCreatedTime());
        assertEquals(historyConfig.getLastModifiedTime(), historyConfig1.getLastModifiedTime());
        assertNull(historyConfig1.getDescription());
        assertNotNull(historyConfig1.getMd5());
        assertNotNull(historyConfig1.getContent());

        // Test getConfig()
        Config config = client.getConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID);
        String id = config.getId();
        assertConfig(config);

        // Test getPreviousHistoryConfig()
        HistoryConfig historyConfig2 = client.getPreviousHistoryConfig(TEST_NAMESPACE_ID, TEST_GROUP_NAME, TEST_DATA_ID, id);
        assertEquals(config.getNamespaceId(), historyConfig2.getNamespaceId());
        assertEquals(config.getGroup(), historyConfig2.getGroup());
        assertEquals(config.getDataId(), historyConfig2.getDataId());
        assertEquals(config.getAppName(), historyConfig2.getAppName());
    }

    private void assertHistoryConfig(HistoryConfig historyConfig) {
        assertGenericConfig(historyConfig);
        assertNotNull(historyConfig.getRevision());
        assertNotNull(historyConfig.getLastRevision());
        assertNotNull(historyConfig.getOperationType());
    }

    private void assertConfig(Config config) {
        assertGenericConfig(config);
        assertNotNull(config.getId());
        assertNotNull(config.getContent());
        assertNotNull(config.getDescription());
        assertNotNull(config.getTags());
        assertNotNull(config.getMd5());
        assertNotNull(config.getUse());
        assertNotNull(config.getEffect());
        assertNotNull(config.getSchema());
        assertNotNull(config.getType());

    }

    private void assertGenericConfig(GenericConfig config) {
        assertEquals(TEST_NAMESPACE_ID, config.getNamespaceId());
        assertEquals(TEST_GROUP_NAME, config.getGroup());
        assertEquals(TEST_DATA_ID, config.getDataId());
        assertEquals(APP_NAME, config.getAppName());
        assertNotNull(config.getOperatorIp());
        assertNotNull(config.getOperator());
        assertNotNull(config.getCreatedTime());
        assertNotNull(config.getLastModifiedTime());
    }

    private NewConfig createNewConfig() {
        NewConfig newConfig = new NewConfig();
        newConfig.setNamespaceId(TEST_NAMESPACE_ID);
        newConfig.setGroup(TEST_GROUP_NAME);
        newConfig.setDataId(TEST_DATA_ID);
        newConfig.setContent(CONTENT);
        newConfig.setTags(TAGS);
        newConfig.setAppName(APP_NAME);
        newConfig.setDescription(DESCRIPTION);
        newConfig.setOperator(OPERATOR);
        newConfig.setUse(USE);
        newConfig.setEffect(EFFECT);
        newConfig.setSchema(SCHEMA);
        newConfig.setType(CONFIG_TYPE);
        return newConfig;
    }
}
