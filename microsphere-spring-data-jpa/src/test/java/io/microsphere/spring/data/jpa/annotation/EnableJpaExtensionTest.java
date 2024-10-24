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
package io.microsphere.spring.data.jpa.annotation;

import io.microsphere.entity.User;
import io.microsphere.jpa.AbstractPersistenceTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * {@link EnableJpaExtension} Test
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EnableJpaExtension
 * @since 1.0.0
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = EnableJpaExtensionTest.class
)
@EnableJpaExtension
public class EnableJpaExtensionTest extends AbstractPersistenceTest {

    @Test
    public void test() {

        final User user = new User("mercyblitz");

        doInEntityManager(entityManager -> {
            // Save
            entityManager.persist(user);
            // Flush
            entityManager.flush();
        });

        doInEntityManager(entityManager -> {
            // Find one
            assertNotNull(entityManager.find(User.class, user.getId()));
        });

        doInEntityManager(entityManager -> {
            // Find one
            entityManager.remove(user);
            assertNull(entityManager.find(User.class, user.getId()));
        });

    }
}
