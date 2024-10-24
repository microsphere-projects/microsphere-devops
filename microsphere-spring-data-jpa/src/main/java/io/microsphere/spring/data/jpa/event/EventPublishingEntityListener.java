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
package io.microsphere.spring.data.jpa.event;

import io.microsphere.jpa.event.EntityLifecycleType;
import io.microsphere.logging.Logger;
import io.microsphere.logging.LoggerFactory;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import org.springframework.context.ApplicationEventPublisher;


/**
 * The Entity Listener for publishing Spring Event when the annotation {@link EntityListeners} associates with current
 * class.
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EntityListeners
 * @since 1.0.0
 */
public class EventPublishingEntityListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static ApplicationEventPublisher applicationEventPublisher;

    @PostLoad
    public void onPostLoad(Object entity) {
        publishEvent(entity, EntityLifecycleType.POST_LOAD);
    }

    @PrePersist
    public void onPrePersist(Object entity) {
        publishEvent(entity, EntityLifecycleType.PRE_PERSIST);
    }

    @PostPersist
    public void onPostPersist(Object entity) {
        publishEvent(entity, EntityLifecycleType.POST_PERSIST);
    }

    @PreUpdate
    public void onPreUpdate(Object entity) {
        publishEvent(entity, EntityLifecycleType.PRE_UPDATE);
    }

    @PostUpdate
    public void onPostUpdate(Object entity) {
        publishEvent(entity, EntityLifecycleType.POST_LOAD);
    }

    @PreRemove
    public void onPreRemove(Object entity) {
        publishEvent(entity, EntityLifecycleType.PRE_REMOVE);
    }

    @PostRemove
    public void onPostRemove(Object entity) {
        publishEvent(entity, EntityLifecycleType.POST_REMOVE);
    }

    private void publishEvent(Object entity, EntityLifecycleType entityLifecycleType) {
        ApplicationEventPublisher applicationEventPublisher = EventPublishingEntityListener.applicationEventPublisher;
        if (applicationEventPublisher == null) {
            logger.warn("No ApplicationEventPublisher set, Please inject ApplicationEventPublisher instance first!");
            return;
        }
        EntityLifecycleEvent event = new EntityLifecycleEvent(entity, entityLifecycleType);
        applicationEventPublisher.publishEvent(event);
    }

    public static void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        EventPublishingEntityListener.applicationEventPublisher = applicationEventPublisher;
    }
}
