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
import io.microsphere.jpa.event.EntityListener;
import io.microsphere.spring.util.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.ResolvableType;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;

import static io.microsphere.spring.util.SpringFactoriesLoaderUtils.loadFactories;
import static org.springframework.core.ResolvableType.forType;
import static org.springframework.core.annotation.AnnotationAwareOrderComparator.sort;

/**
 * The {@link ApplicationListener} listens the {@link EntityLifecycleEvent}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EntityLifecycleEvent
 * @since 1.0.0
 */
public class EntityLifecycleApplicationListener implements ApplicationListener<EntityLifecycleEvent>,
        ApplicationContextAware, SmartInitializingSingleton {

    private final List<EntityListener> entityListeners = new LinkedList<>();

    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(EntityLifecycleEvent event) {
        Object entity = event.getEntity();
        EntityLifecycleType type = event.getType();

        switch (type) {
            case POST_LOAD -> forEach(entity, (e, listener) -> listener.onPostLoad(e));
            case PRE_PERSIST -> forEach(entity, (e, listener) -> listener.onPrePersist(e));
            case POST_PERSIST -> forEach(entity, (e, listener) -> listener.onPostPersist(e));
            case PRE_UPDATE -> forEach(entity, (e, listener) -> listener.onPreUpdate(e));
            case POST_UPDATE -> forEach(entity, (e, listener) -> listener.onPostUpdate(e));
            case PRE_REMOVE -> forEach(entity, (e, listener) -> listener.onPreRemove(e));
            case POST_REMOVE -> forEach(entity, (e, listener) -> listener.onPostRemove(e));
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterSingletonsInstantiated() {
        ApplicationContext applicationContext = this.applicationContext;

        List<EntityListener> entityListenersFromSPI = loadFactories(applicationContext, EntityListener.class);
        List<EntityListener> entityListenerBeans = BeanUtils.getSortedBeans(applicationContext, EntityListener.class);

        List<EntityListener> entityListeners = this.entityListeners;

        entityListeners.addAll(entityListenersFromSPI);
        entityListeners.addAll(entityListenerBeans);

        sort(entityListeners);
    }

    public void addEntityListeners(List<EntityListener> entityListeners) {
        this.entityListeners.addAll(entityListeners);
    }

    private void forEach(Object entity, BiConsumer<Object, EntityListener> consumer) {
        Class<?> entityClass = entity.getClass();
        List<EntityListener> entityListeners = this.entityListeners;
        for (EntityListener entityListener : entityListeners) {
            ResolvableType listenedType = forType(entityListener.getClass())
                    .as(EntityListener.class)
                    .getGeneric(0);
            if (listenedType.isAssignableFrom(entityClass)) {
                consumer.accept(entity, entityListener);
            }
        }
    }
}
