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

import org.junit.jupiter.api.Test;

import static io.microsphere.nacos.client.util.StringUtils.EMPTY_STRING;
import static io.microsphere.nacos.client.util.StringUtils.isBlank;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * {@link StringUtils} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see StringUtils
 * @since 1.0.0
 */
public class StringUtilsTest {

    @Test
    public void testConstants() {
        assertEquals("", EMPTY_STRING);
    }

    @Test
    public void testIsBlank() {
        assertTrue(isBlank(null));
        assertTrue(isBlank(EMPTY_STRING));
        assertTrue(isBlank(" "));
        assertTrue(isBlank("  "));
        assertTrue(isBlank("   "));
        assertFalse(isBlank("___"));
    }
}
