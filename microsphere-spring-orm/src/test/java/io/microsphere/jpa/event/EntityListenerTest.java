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

import io.microsphere.entity.User;
import io.microsphere.jpa.AbstractPersistenceTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * {@link EntityListener} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EntityListener
 * @since 1.0.0
 */
public class EntityListenerTest extends AbstractPersistenceTest {

    @Test
    public void test() {
        final User user = new User("mercyblitz");

        // Save
        doInTransaction(entityManager -> {
            entityManager.persist(user);
        });

        // Find one
        doInTransaction(entityManager -> {
            assertNotNull(entityManager.find(User.class, user.getId()));
        });

        // Save
        doInTransaction(entityManager -> {
            user.setDescription("No Desc");
            entityManager.persist(user);
        });

        // Update
        doInTransaction(entityManager -> {
            user.setDescription("Update Desc");
            entityManager.merge(user);
        });

//        // Find all
        doInTransaction(entityManager -> {
            TypedQuery<User> query = entityManager.createQuery("FROM User", User.class);
            List<User> users = query.getResultList();
            assertEquals(1, users.size());
            assertEquals(user, users.get(0));
        });

        // Delete
        doInTransaction(entityManager -> {
            entityManager.remove(user);
        });

        // Not exists
        doInTransaction(entityManager -> {
            assertNull(entityManager.find(User.class, user.getId()));
        });
    }

    private void doInTransaction(Consumer<EntityManager> entityManagerConsumer) {
        EntityManager entityManager = this.entityManager;
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManagerConsumer.accept(entityManager);
            transaction.commit();
        } catch (Throwable e) {
            transaction.rollback();
        }
    }
}
