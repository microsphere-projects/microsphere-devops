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

import io.microsphere.nacos.client.ErrorCode;

import static java.util.Objects.requireNonNull;

/**
 * The RuntimeException sub-type of Nacos Client Execution
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see RuntimeException
 * @since 1.0.0
 */
public class OpenApiClientException extends RuntimeException {

    private final ErrorCode errorCode;

    public OpenApiClientException(ErrorCode errorCode, String message) {
        this(errorCode, message, null);
    }

    public OpenApiClientException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause, true, false);
        requireNonNull(errorCode, "The 'errorCode' argument must not be null!");
        this.errorCode = errorCode;
    }

    /**
     * The error code
     *
     * @return the error code
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
