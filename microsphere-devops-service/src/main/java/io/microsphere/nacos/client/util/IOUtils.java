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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.microsphere.nacos.client.util.StringUtils.EMPTY_STRING;
import static java.nio.charset.Charset.forName;

/**
 * The utility class for I/O
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see AutoCloseable
 * @since 1.0.0
 */
public abstract class IOUtils {

    /**
     * The property name of the default buffer size
     */
    public static final String DEFAULT_BUFFER_SIZE_PROPERTY_NAME = "microsphere.nacos.client.io.default-buffer-size";

    /**
     * The default buffer size
     */
    public static final int DEFAULT_BUFFER_SIZE = Integer.getInteger(DEFAULT_BUFFER_SIZE_PROPERTY_NAME, 4 * 1024);

    static final int MAX_BUFFER_SIZE = Integer.MAX_VALUE - 8;

    static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * Reads up to a specified number of bytes from the input stream. This
     * method blocks until the requested number of bytes has been read, end
     * of stream is detected, or an exception is thrown. This method does not
     * close the input stream.
     *
     * @param inputStream the {@link InputStream} instance
     * @return a byte array containing the bytes read from this input stream
     * @throws IllegalArgumentException if {@code length} is negative
     * @throws IOException              if an I/O error occurs
     * @throws OutOfMemoryError         if an array of the required size cannot be
     *                                  allocated.
     * @since Java 9
     */
    public static byte[] readAsBytes(InputStream inputStream) throws IOException {
        return readAsBytes(inputStream, Integer.MAX_VALUE);
    }

    /**
     * Reads up to a specified number of bytes from the input stream. This
     * method blocks until the requested number of bytes has been read, end
     * of stream is detected, or an exception is thrown. This method does not
     * close the input stream.
     *
     * <p> The length of the returned array equals the number of bytes read
     * from the stream. If {@code length} is zero, then no bytes are read and
     * an empty byte array is returned. Otherwise, up to {@code length} bytes
     * are read from the stream. Fewer than {@code length} bytes may be read if
     * end of stream is encountered.
     *
     * <p> When this stream reaches end of stream, further invocations of this
     * method will return an empty byte array.
     *
     * <p> Note that this method is intended for simple cases where it is
     * convenient to read the specified number of bytes into a byte array. The
     * total amount of memory allocated by this method is proportional to the
     * number of bytes read from the stream which is bounded by {@code length}.
     * Therefore, the method may be safely called with very large values of
     * {@code length} provided sufficient memory is available.
     *
     * <p> The behavior for the case where the input stream is <i>asynchronously
     * closed</i>, or the thread interrupted during the read, is highly input
     * stream specific, and therefore not specified.
     *
     * <p> If an I/O error occurs reading from the input stream, then it may do
     * so after some, but not all, bytes have been read. Consequently the input
     * stream may not be at end of stream and may be in an inconsistent state.
     * It is strongly recommended that the stream be promptly closed if an I/O
     * error occurs.
     *
     * @param inputStream the {@link InputStream} instance
     * @param length      the maximum number of bytes to read
     * @return a byte array containing the bytes read from this input stream
     * @throws IOException      if an I/O error occurs
     * @throws OutOfMemoryError if an array of the required size cannot be
     *                          allocated.
     * @implNote The number of bytes allocated to read data from this stream and return
     * the result is bounded by {@code 2*(long)length}, inclusive.
     * @since Java 9
     */
    public static byte[] readAsBytes(InputStream inputStream, int length) throws IOException {
        if (inputStream == null || length < 0) {
            return EMPTY_BYTE_ARRAY;
        }

        List<byte[]> bufs = null;
        byte[] result = null;
        int total = 0;
        int remaining = length;
        int n;
        do {
            byte[] buf = new byte[Math.min(remaining, DEFAULT_BUFFER_SIZE)];
            int nread = 0;

            // read to EOF which may read more or less than buffer size
            while ((n = inputStream.read(buf, nread, Math.min(buf.length - nread, remaining))) > 0) {
                nread += n;
                remaining -= n;
            }

            if (nread > 0) {
                if (MAX_BUFFER_SIZE - total < nread) {
                    throw new OutOfMemoryError("Required array size too large");
                }
                if (nread < buf.length) {
                    buf = Arrays.copyOfRange(buf, 0, nread);
                }
                total += nread;
                if (result == null) {
                    result = buf;
                } else {
                    if (bufs == null) {
                        bufs = new ArrayList<>();
                        bufs.add(result);
                    }
                    bufs.add(buf);
                }
            }
            // if the last call to read returned -1 or the number of bytes
            // requested have been read then break
        } while (n >= 0 && remaining > 0);

        if (bufs == null) {
            if (result == null) {
                return new byte[0];
            }
            return result.length == total ? result : Arrays.copyOf(result, total);
        }

        result = new byte[total];
        int offset = 0;
        remaining = total;
        for (byte[] b : bufs) {
            int count = Math.min(b.length, remaining);
            System.arraycopy(b, 0, result, offset, count);
            offset += count;
            remaining -= count;
        }

        return result;
    }

    /**
     * Read the content as {@link String} from the specified {@link InputStream} and encoding
     *
     * @param inputStream {@link InputStream}
     * @param encoding    the encoding
     * @return the {@link String} content
     * @throws IOException if read failed
     */
    public static String readAsString(InputStream inputStream, String encoding) throws IOException {
        if (inputStream == null) {
            return EMPTY_STRING;
        }
        return readAsString(inputStream, forName(encoding));
    }

    /**
     * Read the content as {@link String} from the specified {@link InputStream} and {@link Charset}
     *
     * @param inputStream {@link InputStream}
     * @param charset     {@link Charset}
     * @return the {@link String} content
     * @throws IOException if read failed
     */
    public static String readAsString(InputStream inputStream, Charset charset) throws IOException {
        byte[] bytes = readAsBytes(inputStream);
        return bytes == EMPTY_BYTE_ARRAY ? EMPTY_STRING : new String(bytes, charset);
    }
}
