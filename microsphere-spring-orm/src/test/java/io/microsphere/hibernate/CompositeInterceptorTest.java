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

import io.microsphere.entity.User;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * {@link CompositeInterceptor} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see CompositeInterceptor
 * @since 1.0.0
 */
public class CompositeInterceptorTest extends AbstractHibernateTest {

    private CompositeInterceptor interceptor;

    @BeforeEach
    public void init() {
        this.interceptor = new CompositeInterceptor();
    }

    @Override
    protected void setupConfiguration(Configuration configuration) {
        configuration.addAnnotatedClass(User.class);
        CompositeInterceptor interceptor = new CompositeInterceptor();
        interceptor.addInterceptor(new MockInterceptor());
        configuration.setInterceptor(interceptor);
    }

    @Test
    public void testPersist() {
        sessionFactory.inTransaction(session -> {
            String entityName = "user";
            User user = new User("mercyblitz");
            // Save
            session.persist(entityName, user);
            // Flush
            session.flush();
            // Find one
            user = session.find(User.class, user.getId());
            // Update
            session.merge(entityName, user);
            // Find all
            Query<User> query = session.createQuery("FROM User", User.class);
            List<User> users = query.list();
            assertEquals(1, users.size());
            assertEquals(user, users.get(0));

            // Get
            user = session.get(User.class, user.getId());

            // Delete
            session.remove(user);

            // Not exists
            assertNull(session.get(User.class, user.getId()));
        });
    }
}
