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
package io.microsphere.nacos.client.transport;

import java.lang.reflect.Type;

/**
 * The enumeration for Open API Request Headers
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see OpenApiRequest
 * @see OpenApiRequestParam
 * @since 1.0.0
 */
public enum OpenApiRequestHeader {

    LONG_PULLING_TIMEOUT("Long-Pulling-Timeout");

    /**
     * The request header name
     */
    private final String name;

    /**
     * The request header type
     */
    private final Type type;

    OpenApiRequestHeader(String name) {
        this(name, String.class);
    }

    OpenApiRequestHeader(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Get the request header name
     *
     * @return non-null
     */
    public String getName() {
        return name;
    }

    /**
     * Get the request header type
     *
     * @return non-null
     */
    public Type getType() {
        return type;
    }

    public String toValue(Object rawValue) {
        return rawValue == null ? null : rawValue.toString();
    }
}
