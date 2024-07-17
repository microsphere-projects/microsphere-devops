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

/**
 * The enumeration of Nacos Configuration Operation Type
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Enum
 * @since 1.0.0
 */
public enum ConfigOperationType {

    INSERT("I"),
    UPDATE("U"),
    DELETE("D"),

    ;

    private final String value;

    ConfigOperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ConfigOperationType of(String value) {
        for (ConfigOperationType type : values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported ConfigOperationType value : " + value);
    }
}
