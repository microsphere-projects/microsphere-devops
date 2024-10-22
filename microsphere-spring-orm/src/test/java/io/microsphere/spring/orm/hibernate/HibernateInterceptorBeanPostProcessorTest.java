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
package io.microsphere.spring.orm.hibernate;

import io.microsphere.entity.User;
import io.microsphere.hibernate.MockInterceptor;
import io.microsphere.logging.Logger;
import io.microsphere.logging.LoggerFactory;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * {@link HibernateInterceptorBeanPostProcessor} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see HibernateInterceptorBeanPostProcessor
 * @since 1.0.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        MockInterceptor.class,
        HibernateTestConfiguration.class,
        HibernateInterceptorBeanPostProcessorTest.class
})
public class HibernateInterceptorBeanPostProcessorTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void test() {
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
