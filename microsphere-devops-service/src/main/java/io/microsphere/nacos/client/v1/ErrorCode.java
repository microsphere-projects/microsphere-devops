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
package io.microsphere.nacos.client.v1;

/**
 * The Enumeration for Nacos OpenAPI V1 Error-Code
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
public enum ErrorCode {

    OK(200, "OK"),

    BAD_REQUEST(400, "Bad Request"),

    FORBIDDEN(403, "No Permission Request"),

    NOT_FOUND(404, "The resource is not found"),

    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    ;


    private final int value;

    private final String description;

    ErrorCode(int value, String description) {
        this.value = value;
        this.description = description;
    }

    /**
     * @return The value of {@link ErrorCode}
     */
    public int getValue() {
        return value;
    }

    /**
     * @return The description of {@link ErrorCode}
     */
    public String getDescription() {
        return description;
    }
}
