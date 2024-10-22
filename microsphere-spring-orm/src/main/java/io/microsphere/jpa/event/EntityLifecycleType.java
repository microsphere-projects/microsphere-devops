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
package io.microsphere.jpa.event;


import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

/**
 * The enumeration of entity lifecycle types
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see PostLoad
 * @see PostPersist
 * @see PostRemove
 * @see PostUpdate
 * @see PrePersist
 * @see PreRemove
 * @see PreUpdate
 * @since 1.0.0
 */
public enum EntityLifecycleType {

    /**
     * @see PostLoad
     */
    POST_LOAD,

    /**
     * @see PostLoad
     */
    PRE_PERSIST,

    /**
     * @see PostPersist
     */
    POST_PERSIST,

    /**
     * @see PreUpdate
     */
    PRE_UPDATE,

    /**
     * @see PostUpdate
     */
    POST_UPDATE,

    /**
     * @see PreRemove
     */
    PRE_REMOVE,

    /**
     * @see PostRemove
     */
    POST_REMOVE,
}
