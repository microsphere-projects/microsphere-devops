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

import io.microsphere.jpa.event.EntityType;
import io.microsphere.logging.Logger;
import io.microsphere.logging.LoggerFactory;
import io.microsphere.spring.data.jpa.annotation.EntityListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationListenerMethodAdapter;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static io.microsphere.util.ArrayUtils.EMPTY_OBJECT_ARRAY;
import static org.springframework.core.ResolvableType.forClass;
import static org.springframework.core.ResolvableType.forType;
import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

/**
 * The {@link ApplicationListener} Adapter class for {@link EntityEvent}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EntityEvent
 * @see EntityListener
 * @see ApplicationListenerMethodAdapter
 * @since 1.0.0
 */
class EntityApplicationListenerMethodAdapter extends ApplicationListenerMethodAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EntityApplicationListenerMethodAdapter.class);

    private final EntityType[] entityTypes;

    public EntityApplicationListenerMethodAdapter(String beanName, Class<?> targetClass, Method method) {
        super(beanName, targetClass, method);
        this.entityTypes = resolveEntityLifecycleTypes();
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return supportsEventType(forClass(eventType));
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == null ? true : EventPublishingEntityListener.class.equals(sourceType);
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.isAssignableFrom(EntityEvent.class);
    }

    public boolean supportsAsyncExecution() {
        return true;
    }

    /**
     * Resolve the method arguments to use for the specified {@link ApplicationEvent}.
     * <p>These arguments will be used to invoke the method handled by this instance.
     * Can return {@code null} to indicate that no suitable arguments could be resolved
     * and therefore the method should not be invoked at all for the specified event.
     */
    @Nullable
    protected Object[] resolveArguments(ApplicationEvent event) {
        Method method = this.getTargetMethod();

        int parameterCount = method.getParameterCount();

        if (parameterCount != 1) {
            return EMPTY_OBJECT_ARRAY;
        }

        if (event instanceof EntityEvent) {
            EntityEvent entityEvent = (EntityEvent) event;
            if (matchesEntityLifecycleType(entityEvent)) {
                Type[] parameterTypes = method.getGenericParameterTypes();
                Object[] arguments = new Object[parameterCount];
                for (int i = 0; i < parameterCount; i++) {
                    ResolvableType parameterType = forType(parameterTypes[i]);
                    if (parameterType.isInstance(entityEvent)) {
                        arguments[i] = entityEvent;
                    } else {
                        Object entity = entityEvent.getPayload();
                        if (parameterType.isInstance(entity)) {
                            arguments[i] = entity;
                        }
                    }
                }
                return arguments;
            }
        }
        return null;
    }

    private boolean matchesEntityLifecycleType(EntityEvent event) {
        EntityType[] entityTypes = this.entityTypes;
        int length = entityTypes.length;
        if (length == 1) {
            return entityTypes[0].equals(event.getType());
        }
        boolean result = false;
        for (int i = 0; i < length; i++) {
            if (entityTypes[i].equals(event.getType())) {
                result = true;
                break;
            }
        }
        return result;
    }

    private EntityType[] resolveEntityLifecycleTypes() {
        EntityListener listener = findMergedAnnotation(getTargetMethod(), EntityListener.class);
        return listener.type();
    }
}
