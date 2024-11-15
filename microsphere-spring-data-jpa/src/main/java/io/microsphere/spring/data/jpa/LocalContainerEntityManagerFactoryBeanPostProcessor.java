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
package io.microsphere.spring.data.jpa;

import io.microsphere.spring.beans.factory.config.GenericBeanPostProcessorAdapter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

/**
 * The {@link BeanPostProcessor} for {@link LocalContainerEntityManagerFactoryBean}'s pre-initialization
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see LocalContainerEntityManagerFactoryBean
 * @see GenericBeanPostProcessorAdapter
 * @see BeanPostProcessor
 * @since 1.0.0
 */
public class LocalContainerEntityManagerFactoryBeanPostProcessor
        extends GenericBeanPostProcessorAdapter<LocalContainerEntityManagerFactoryBean> implements PersistenceUnitPostProcessor {

    private static final String DEFAULT_MAPPING_RESOURCE = "META-INF/spring/data/jpa/orm.xml";

    @Override
    protected void processBeforeInitialization(LocalContainerEntityManagerFactoryBean bean, String beanName) throws BeansException {
        bean.setPersistenceUnitPostProcessors(this);
    }

    @Override
    public void postProcessPersistenceUnitInfo(MutablePersistenceUnitInfo pui) {
        pui.addMappingFileName(DEFAULT_MAPPING_RESOURCE);
    }
}
