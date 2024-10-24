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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Mock {@link Interceptor}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Interceptor
 * @since 1.0.0
 */
public class MockInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        logger.info("onLoad({}, {}, {}, {}, {})", entity, id, state, propertyNames, Arrays.toString(types));
        return false;
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) throws CallbackException {
        logger.info("onFlushDirty({}, {}, {}, {}, {}, {})", entity, id, Arrays.toString(currentState),
                Arrays.toString(previousState), propertyNames, Arrays.toString(types));

        return false;
    }

    @Override
    public boolean onSave(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        logger.info("onSave({}, {}, {}, {}, {}, {})", entity, id, state, Arrays.toString(propertyNames), Arrays.toString(types));
        return false;
    }

    @Override
    public void onDelete(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        logger.info("onDelete({}, {}, {}, {}, {}, {})", entity, id, state, Arrays.toString(propertyNames), Arrays.toString(types));
    }

    @Override
    public void onCollectionRecreate(Object collection, Object key) throws CallbackException {
        logger.info("onCollectionRecreate({}, {})", collection, key);
    }

    @Override
    public void onCollectionRemove(Object collection, Object key) throws CallbackException {
        logger.info("onCollectionRemove({}, {})", collection, key);
    }

    @Override
    public void onCollectionUpdate(Object collection, Object key) throws CallbackException {
        logger.info("onCollectionUpdate({}, {})", collection, key);
    }

    @Override
    public void preFlush(Iterator<Object> entities) throws CallbackException {
        logger.info("preFlush({})", entities);
    }

    @Override
    public void postFlush(Iterator<Object> entities) throws CallbackException {
        logger.info("postFlush({})", entities);
    }

    @Override
    public Boolean isTransient(Object entity) {
        logger.info("isTransient({})", entity);
        return true;
    }

    @Override
    public int[] findDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        logger.info("findDirty({}, {}, {}, {}, {}, {})", entity, id, Arrays.toString(currentState),
                Arrays.toString(previousState), Arrays.toString(propertyNames), Arrays.toString(types));
        return null;
    }

    @Override
    public Object instantiate(String entityName, EntityRepresentationStrategy representationStrategy, Object id) throws CallbackException {
        logger.info("instantiate({}, {}, {})", entityName, representationStrategy, id);
        return null;
    }

    @Override
    public Object instantiate(String entityName, RepresentationMode representationMode, Object id) throws CallbackException {
        logger.info("instantiate({}, {}, {})", entityName, representationMode, id);
        return null;
    }

    @Override
    public String getEntityName(Object object) throws CallbackException {
        logger.info("getEntityName({})", object);
        return null;
    }

    @Override
    public Object getEntity(String entityName, Object id) throws CallbackException {
        logger.info("getEntity({}, {})", entityName, id);
        return null;
    }

    @Override
    public void afterTransactionBegin(Transaction tx) {
        logger.info("afterTransactionBegin({})", tx);
    }

    @Override
    public void beforeTransactionCompletion(Transaction tx) {
        logger.info("beforeTransactionCompletion({})", tx);
    }

    @Override
    public void afterTransactionCompletion(Transaction tx) {
        logger.info("afterTransactionCompletion({})", tx);
    }
}
