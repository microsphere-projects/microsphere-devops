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

import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hibernate.cfg.JdbcSettings.FORMAT_SQL;
import static org.hibernate.cfg.JdbcSettings.HIGHLIGHT_SQL;
import static org.hibernate.cfg.JdbcSettings.PASS;
import static org.hibernate.cfg.JdbcSettings.SHOW_SQL;
import static org.hibernate.cfg.JdbcSettings.URL;
import static org.hibernate.cfg.JdbcSettings.USER;
import static org.hibernate.cfg.SchemaToolingSettings.HBM2DDL_AUTO;

/**
 * The abstract test for Hibernate
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see Hibernate
 * @since 1.0.0
 */
public abstract class AbstractHibernateTest {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected SessionFactory sessionFactory;

    @BeforeEach
    public void setupSessionFactory() {
        Configuration configuration = new Configuration()
                // use H2 in-memory database
                .setProperty(URL, "jdbc:h2:mem:db1")
                .setProperty(USER, "sa")
                .setProperty(PASS, "")
                // display SQL in console
                .setProperty(SHOW_SQL, true)
                .setProperty(FORMAT_SQL, true)
                .setProperty(HIGHLIGHT_SQL, true)
                .setProperty(HBM2DDL_AUTO, "create");

        setupConfiguration(configuration);

        this.sessionFactory = configuration.buildSessionFactory();
    }

    protected void setupConfiguration(Configuration configuration) {
    }
}
