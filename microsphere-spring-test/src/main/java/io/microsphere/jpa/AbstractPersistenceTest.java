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
package io.microsphere.jpa;

import io.microsphere.logging.Logger;
import io.microsphere.logging.LoggerFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;

import java.util.function.Consumer;

/**
 * The abstract test for JPA
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Hibernate
 * @since 1.0.0
 */
public abstract class AbstractPersistenceTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected EntityManager entityManager;

    @BeforeEach
    public void setupEntityManager() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("io.microsphere.entity");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    protected void doInEntityManager(Consumer<EntityManager> entityManagerConsumer) {
        EntityManager entityManager = this.entityManager;
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManagerConsumer.accept(entityManager);
            transaction.commit();
        } catch (Throwable e) {
            transaction.rollback();
        }
    }
}
