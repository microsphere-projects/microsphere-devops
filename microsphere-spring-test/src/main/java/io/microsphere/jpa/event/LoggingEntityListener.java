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

import io.microsphere.logging.Logger;
import io.microsphere.logging.LoggerFactory;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

/**
 * EntityListener for Logging
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @since 1.0.0
 */
public class LoggingEntityListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostLoad
    public void onPostLoad(Object entity) {
        logger.info("onPostLoad({})", entity);
    }

    @PrePersist
    public void onPrePersist(Object entity) {
        logger.info("onPrePersist({})", entity);
    }

    @PostPersist
    public void onPostPersist(Object entity) {
        logger.info("onPostPersist({})", entity);
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        logger.info("onPreUpdate({})", entity);
    }

    @PostUpdate
    public void onPostUpdate(Object entity) {
        logger.info("onPostUpdate({})", entity);
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        logger.info("onPreRemove({})", entity);
    }

    @PostRemove
    public void onPostRemove(Object entity) {
        logger.info("onPostRemove({})", entity);
    }
}
