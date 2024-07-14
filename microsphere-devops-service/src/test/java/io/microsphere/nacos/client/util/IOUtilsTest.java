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

import java.io.ByteArrayInputStream;

import static io.microsphere.nacos.client.util.IOUtils.DEFAULT_BUFFER_SIZE;
import static io.microsphere.nacos.client.util.IOUtils.DEFAULT_BUFFER_SIZE_PROPERTY_NAME;
import static io.microsphere.nacos.client.util.IOUtils.EMPTY_BYTE_ARRAY;
import static io.microsphere.nacos.client.util.IOUtils.MAX_BUFFER_SIZE;
import static io.microsphere.nacos.client.util.IOUtils.readAsBytes;
import static io.microsphere.nacos.client.util.IOUtils.readAsString;
import static io.microsphere.nacos.client.util.StringUtils.EMPTY_STRING;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link IOUtils} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see IOUtils
 * @since 1.0.0
 */
public class IOUtilsTest {

    private static final int TEST_BUFFER_SIZE = 1024;

    private static final String ENCODING = System.getProperty("file.encoding");

    private static final byte[] TEST_BYTES;

    static {
        System.setProperty(DEFAULT_BUFFER_SIZE_PROPERTY_NAME, String.valueOf(TEST_BUFFER_SIZE));
        try {
            TEST_BYTES = ENCODING.getBytes(ENCODING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testConstants() {
        assertEquals("microsphere.nacos.client.io.default-buffer-size", DEFAULT_BUFFER_SIZE_PROPERTY_NAME);
        assertEquals(TEST_BUFFER_SIZE, DEFAULT_BUFFER_SIZE);
        assertEquals(2147483639, MAX_BUFFER_SIZE);
        assertArrayEquals(new byte[0], EMPTY_BYTE_ARRAY);
    }

    @Test
    public void testReadAsBytes() throws Exception {
        assertArrayEquals(EMPTY_BYTE_ARRAY, readAsBytes(null));
        assertArrayEquals(EMPTY_BYTE_ARRAY, readAsBytes(new ByteArrayInputStream(TEST_BYTES), 0));
        assertArrayEquals(TEST_BYTES, readAsBytes(new ByteArrayInputStream(TEST_BYTES)));
        assertArrayEquals(TEST_BYTES, readAsBytes(new ByteArrayInputStream(TEST_BYTES), TEST_BYTES.length));
    }

    @Test
    public void testReadAsString() throws Exception {
        assertEquals(EMPTY_STRING, readAsString(null, ENCODING));
        assertEquals(ENCODING, readAsString(new ByteArrayInputStream(TEST_BYTES), ENCODING));
    }
}
