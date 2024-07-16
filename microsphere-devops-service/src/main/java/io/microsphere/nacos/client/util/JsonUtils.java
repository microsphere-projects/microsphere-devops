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
package io.microsphere.nacos.client.util;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * The utility class for JSON
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
public abstract class JsonUtils {

    public static String toJSON(List<Map<String, String>> maps) {
        int size = maps == null ? 0 : maps.size();
        if (size < 1) {
            return "[]";
        }

        StringJoiner jsonBuilder = new StringJoiner(",", "[", "]");
        for (int i = 0; i < size; i++) {
            Map<String, String> map = maps.get(i);
            jsonBuilder.add(toJSON(map));
        }

        return jsonBuilder.toString();
    }

    /**
     * Create a new JSON String from the specified {@link Map}
     *
     * @param map {@link Map}
     * @return non-null
     */
    public static String toJSON(Map<String, String> map) {
        int size = map == null ? 0 : map.size();
        if (size < 1) {
            return "{}";
        }

        StringBuilder jsonBuilder = new StringBuilder(32 * size);

        int cursor = 0;
        jsonBuilder.append("{");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            jsonBuilder.append('"')
                    .append(key)
                    .append('"')
                    .append(':')
                    .append('"')
                    .append(value)
                    .append('"');
            if (++cursor < size) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
