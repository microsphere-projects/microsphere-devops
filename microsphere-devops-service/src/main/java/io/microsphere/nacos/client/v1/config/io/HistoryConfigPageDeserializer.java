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
package io.microsphere.nacos.client.v1.config.io;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import io.microsphere.nacos.client.common.model.Page;
import io.microsphere.nacos.client.io.GsonDeserializer;
import io.microsphere.nacos.client.v1.config.ConfigClient;
import io.microsphere.nacos.client.v1.config.model.HistoryConfig;
import io.microsphere.nacos.client.v1.config.model.HistoryConfigPage;

import java.lang.reflect.Type;
import java.util.List;

/**
 * The {@link GsonDeserializer} class for {@link HistoryConfigPage}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see HistoryConfigPage
 * @see HistoryConfig
 * @see Page
 * @see HistoryConfigDeserializer
 * @see ConfigClient#getHistoryConfigs(String, String, String, int, int)
 * @since 1.0.0
 */
public class HistoryConfigPageDeserializer extends GsonDeserializer<HistoryConfigPage> {

    private static final String TOTAL_COUNT_MEMBER_NAME = "totalCount";

    private static final String PAGE_NUMBER_MEMBER_NAME = "pageNumber";

    private static final String PAGE_AVAILABLE_MEMBER_NAME = "pagesAvailable";

    private static final String PAGE_ITEMS_MEMBER_NAME = "pageItems";

    @Override
    public HistoryConfigPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int totalPages = getInteger(jsonObject, PAGE_AVAILABLE_MEMBER_NAME);
        int pageNumber = getInteger(jsonObject, PAGE_NUMBER_MEMBER_NAME);
        int totalElements = getInteger(jsonObject, TOTAL_COUNT_MEMBER_NAME);
        JsonArray pageItemsJsonArray = jsonObject.getAsJsonArray(PAGE_ITEMS_MEMBER_NAME);
        List<HistoryConfig> historyConfigs = context.deserialize(pageItemsJsonArray, HistoryConfigList.class);
        HistoryConfigPage page = new HistoryConfigPage(totalPages, totalElements, historyConfigs);
        page.setPageNumber(pageNumber);
        return page;
    }

    private static interface HistoryConfigList extends List<HistoryConfig> {

    }
}
