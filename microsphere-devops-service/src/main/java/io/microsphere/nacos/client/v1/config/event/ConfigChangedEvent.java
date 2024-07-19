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
package io.microsphere.nacos.client.v1.config.event;

import io.microsphere.nacos.client.v1.config.model.Config;

import java.util.EventObject;

import static io.microsphere.nacos.client.v1.config.event.ConfigChangedEvent.Kind.CREATED;
import static io.microsphere.nacos.client.v1.config.event.ConfigChangedEvent.Kind.DELETED;
import static io.microsphere.nacos.client.v1.config.event.ConfigChangedEvent.Kind.MODIFIED;

/**
 * The {@link EventObject Event} raised when the Nacos {@link Config} is changed
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EventObject
 * @see Config
 * @since 1.0.0
 */
public class ConfigChangedEvent extends EventObject {

    private final Config previous;

    private final Config current;

    private final Kind kind;

    protected ConfigChangedEvent(Config previous, Config current, Kind kind) {
        super(current == null ? previous : current);
        this.previous = previous;
        this.current = current;
        this.kind = kind;
    }

    public static ConfigChangedEvent ofCreated(Config current) {
        return new ConfigChangedEvent(null, current, CREATED);
    }

    public static ConfigChangedEvent ofModified(Config previous, Config current) {
        return new ConfigChangedEvent(previous, current, MODIFIED);
    }

    public static ConfigChangedEvent ofDeleted(Config previous) {
        return new ConfigChangedEvent(previous, null, DELETED);
    }

    /**
     * Get the previous {@link Config}
     *
     * @return <code>null</code> if {@link #isCreated()}
     */
    public Config getPrevious() {
        return this.previous;
    }

    /**
     * Get the current {@link Config}
     *
     * @return <code>null</code> if {@link #isDeleted()}
     */
    public Config getCurrent() {
        return this.current;
    }

    /**
     * Get the {@link Kind}
     *
     * @return non-null
     */
    public Kind getKind() {
        return this.kind;
    }

    /**
     * Whether the {@link Config} was created
     *
     * @return <code>true</code> if the {@link Config} was created
     */
    public boolean isCreated() {
        return CREATED.equals(getKind());
    }

    /**
     * Whether the {@link Config} was modified
     *
     * @return <code>true</code> if the {@link Config} was modified
     */
    public boolean isModified() {
        return MODIFIED.equals(getKind());
    }

    /**
     * Whether the {@link Config} was deleted
     *
     * @return <code>true</code> if the {@link Config} was deleted
     */
    public boolean isDeleted() {
        return DELETED.equals(getKind());
    }

    /**
     * Get the {@link Config#getNamespaceId() namespaceId}
     *
     * @return non-null
     */
    public String getNamespaceId() {
        return getConfig().getNamespaceId();
    }

    /**
     * Get the {@link Config#getGroup() group}
     *
     * @return non-null
     */
    public String getGroup() {
        return getConfig().getGroup();
    }

    /**
     * Get the {@link Config#getDataId() dataId}
     *
     * @return non-null
     */
    public String getDataId() {
        return getConfig().getDataId();
    }

    /**
     * Get the {@link Config#getContent() content} of {@link #getPrevious() preious} or {@link #getCurrent() current}
     * {@link Config}
     *
     * @return non-null
     */
    public String getContent() {
        return getConfig().getContent();
    }

    /**
     * Get the {@link Config#getLastModifiedTime() last modified time}  of {@link #getPrevious() preious} or
     * {@link #getCurrent() current} {@link Config}
     *
     * @return non-null
     */
    public Long getLastModifiedTime() {
        return getConfig().getLastModifiedTime();
    }

    protected Config getConfig() {
        return this.current == null ? this.previous : this.current;
    }

    @Override
    public String toString() {
        return "ConfigChangedEvent{" +
                "previous=" + previous +
                ", current=" + current +
                ", kind=" + kind +
                '}';
    }

    /**
     * The kind of {@link ConfigChangedEvent}
     */
    public static enum Kind {

        /**
         * The {@link Config} was created in first time
         */
        CREATED,

        /**
         * The {@link Config} was modified
         */
        MODIFIED,

        /**
         * The {@link Config} was deleted
         */
        DELETED
    }
}
