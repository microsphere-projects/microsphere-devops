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

import java.io.Serializable;
import java.util.Iterator;

/**
 * Delegating {@link Interceptor}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Interceptor
 * @since 1.0.0
 */
public class DelegatingInterceptor implements Interceptor {

    private final Interceptor delegate;

    public DelegatingInterceptor(Interceptor delegate) {
        this.delegate = delegate;
    }

    @Deprecated(since = "6.0")
    @Override
    public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        return delegate.onLoad(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        return delegate.onLoad(entity, id, state, propertyNames, types);
    }

    @Deprecated(since = "6.0")
    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        return delegate.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        return delegate.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Deprecated(since = "6.0")
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        return delegate.onSave(entity, id, state, propertyNames, types);
    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        return delegate.onSave(entity, id, state, propertyNames, types);
    }

    @Deprecated(since = "6.0")
    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        delegate.onDelete(entity, id, state, propertyNames, types);
    }

    @Override
    public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        delegate.onDelete(entity, id, state, propertyNames, types);
    }

    @Deprecated(since = "6.0")
    @Override
    public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
        delegate.onCollectionRecreate(collection, key);
    }

    @Override
    public void onCollectionRecreate(Object collection, Object key) throws CallbackException {
        delegate.onCollectionRecreate(collection, key);
    }

    @Deprecated(since = "6.0")
    @Override
    public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
        delegate.onCollectionRemove(collection, key);
    }

    @Override
    public void onCollectionRemove(Object collection, Object key) throws CallbackException {
        delegate.onCollectionRemove(collection, key);
    }

    @Deprecated(since = "6.0")
    public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
        delegate.onCollectionUpdate(collection, key);
    }

    @Override
    public void onCollectionUpdate(Object collection, Object key) throws CallbackException {
        delegate.onCollectionUpdate(collection, key);
    }

    @Override
    public void preFlush(Iterator entities) throws CallbackException {
        delegate.preFlush(entities);
    }

    @Override
    public void postFlush(Iterator entities) throws CallbackException {
        delegate.postFlush(entities);
    }

    @Override
    public Boolean isTransient(Object entity) {
        return delegate.isTransient(entity);
    }

    @Deprecated(since = "6.0")
    @Override
    public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        return delegate.findDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public int[] findDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        return delegate.findDirty(entity, id, currentState, previousState, propertyNames, types);
    }

    @Override
    public String getEntityName(Object object) throws CallbackException {
        return delegate.getEntityName(object);
    }

    @Deprecated(since = "6.0")
    @Override
    public Object getEntity(String entityName, Serializable id) throws CallbackException {
        return delegate.getEntity(entityName, id);
    }

    @Override
    public Object getEntity(String entityName, Object id) throws CallbackException {
        return delegate.getEntity(entityName, id);
    }

    @Override
    public void beforeTransactionCompletion(Transaction tx) {
        delegate.beforeTransactionCompletion(tx);
    }

    @Override
    public void afterTransactionBegin(Transaction tx) {
        delegate.afterTransactionBegin(tx);
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        delegate.afterTransactionCompletion(tx);
    }

    @Override
    public Object instantiate(String entityName, EntityRepresentationStrategy representationStrategy, Object id) throws CallbackException {
        return delegate.instantiate(entityName, representationStrategy, id);
    }

    @Override
    public Object instantiate(String entityName, RepresentationMode representationMode, Object id) throws CallbackException {
        return delegate.instantiate(entityName, representationMode, id);
    }

    public Interceptor getDelegate() {
        return delegate;
    }
}


