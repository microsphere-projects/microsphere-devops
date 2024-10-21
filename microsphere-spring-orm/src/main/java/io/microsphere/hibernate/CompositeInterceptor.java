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
package io.microsphere.hibernate;

import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.metamodel.RepresentationMode;
import org.hibernate.metamodel.spi.EntityRepresentationStrategy;
import org.hibernate.type.Type;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The Composite {@link Interceptor} implementation.
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Interceptor
 * @see DelegatingInterceptor
 * @since 1.0.0
 */
public class CompositeInterceptor implements Interceptor {

    private final List<Interceptor> interceptors;

    public CompositeInterceptor() {
        this.interceptors = new LinkedList<>();
    }

    public CompositeInterceptor addInterceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }

    public CompositeInterceptor addInterceptors(Interceptor... interceptors) {
        return addInterceptors(Arrays.asList(interceptors));
    }

    public CompositeInterceptor addInterceptors(Iterable<Interceptor> interceptors) {
        interceptors.forEach(this.interceptors::add);
        return this;
    }

    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        return forEach(e -> e.onLoad(entity, id, state, propertyNames, types), Boolean.FALSE::equals);
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        return forEach(e -> e.onFlushDirty(entity, id, currentState, previousState, propertyNames, types), Boolean.FALSE::equals);
    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        return forEach(e -> e.onSave(entity, id, state, propertyNames, types), Boolean.FALSE::equals);
    }

    @Override
    public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        forEach(e -> e.onDelete(entity, id, state, propertyNames, types));
    }

    @Override
    public void onCollectionRecreate(Object collection, Object key) throws CallbackException {
        forEach(e -> e.onCollectionRecreate(collection, key));
    }

    @Override
    public void onCollectionRemove(Object collection, Object key) throws CallbackException {
        forEach(e -> e.onCollectionRemove(collection, key));
    }

    @Override
    public void onCollectionUpdate(Object collection, Object key) throws CallbackException {
        forEach(e -> e.onCollectionUpdate(collection, key));
    }

    @Override
    public void preFlush(Iterator<Object> entities) throws CallbackException {
        forEach(e -> e.preFlush(entities));
    }

    @Override
    public void postFlush(Iterator<Object> entities) throws CallbackException {
        forEach(e -> e.postFlush(entities));
    }

    @Override
    public Boolean isTransient(Object entity) {
        return forEach(e -> e.isTransient(entity), Boolean.TRUE::equals);
    }

    @Override
    public int[] findDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        return forEach(e -> e.findDirty(entity, id, currentState, previousState, propertyNames, types), Objects::nonNull);
    }

    @Override
    public Object instantiate(String entityName, EntityRepresentationStrategy representationStrategy, Object id) throws CallbackException {
        return forEach(e -> e.instantiate(entityName, representationStrategy, id), Objects::nonNull);
    }

    @Override
    public Object instantiate(String entityName, RepresentationMode representationMode, Object id) throws CallbackException {
        return forEach(e -> e.instantiate(entityName, representationMode, id), Objects::nonNull);
    }

    @Override
    public String getEntityName(Object object) throws CallbackException {
        return forEach(e -> e.getEntityName(object), Objects::nonNull);
    }

    @Override
    public Object getEntity(String entityName, Object id) throws CallbackException {
        return forEach(e -> e.getEntity(entityName, id), Objects::nonNull);
    }

    @Override
    public void afterTransactionBegin(Transaction tx) {
        forEach(e -> e.afterTransactionBegin(tx));
    }

    @Override
    public void beforeTransactionCompletion(Transaction tx) {
        forEach(e -> e.beforeTransactionCompletion(tx));
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        forEach(e -> e.afterTransactionCompletion(tx));
    }

    private <T> T forEach(Function<Interceptor, T> function, Predicate<T> matcher) {
        int size = this.interceptors.size();
        T result = null;
        for (int i = 0; i < size; i++) {
            Interceptor interceptor = this.interceptors.get(i);
            if (interceptor != null) {
                T returnValue = function.apply(interceptor);
                if (result == null && matcher.test(returnValue)) {
                    result = returnValue;
                }
            }
        }
        return result;
    }

    private void forEach(Consumer<Interceptor> consumer) {
        int size = this.interceptors.size();
        for (int i = 0; i < size; i++) {
            Interceptor interceptor = this.interceptors.get(i);
            if (interceptor != null) {
                consumer.accept(interceptor);
            }
        }
    }
}
