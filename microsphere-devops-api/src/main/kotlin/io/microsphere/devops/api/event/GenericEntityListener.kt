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
package io.microsphere.devops.api.event

import io.microsphere.devops.api.commons.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.PostLoad
import jakarta.persistence.PostPersist
import jakarta.persistence.PostRemove
import jakarta.persistence.PostUpdate
import jakarta.persistence.PrePersist
import jakarta.persistence.PreRemove
import jakarta.persistence.PreUpdate
import org.springframework.aop.interceptor.ExposeInvocationInterceptor.currentInvocation

/**
 * {@link EntityListeners}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EntityListeners
 * @since 1.0.0
 */
class GenericEntityListener {

    @PostLoad
    fun onPostLoad(entity: Entity) {
    }


    @PrePersist
    fun onPrePersist(entity: Entity) {
        val methodInvocation = currentInvocation();
    }

    @PostPersist
    fun onPostPersist(entity: Entity) {

    }

    @PreUpdate
    fun onPreUpdate(entity: Entity) {

    }

    @PostUpdate
    fun onPostUpdate(entity: Entity) {

    }

    @PreRemove
    fun onPreRemove(entity: Entity) {

    }

    @PostRemove
    fun onPostRemove(entity: Entity) {

    }
}