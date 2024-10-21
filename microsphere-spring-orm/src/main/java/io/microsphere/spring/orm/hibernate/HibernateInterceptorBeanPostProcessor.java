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

import io.microsphere.hibernate.CompositeInterceptor;
import io.microsphere.hibernate.DelegatingInterceptor;
import io.microsphere.reflect.FieldUtils;
import io.microsphere.spring.beans.factory.config.GenericBeanPostProcessorAdapter;
import org.hibernate.Interceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.List;

import static io.microsphere.spring.util.SpringFactoriesLoaderUtils.loadFactories;

/**
 * {@link LocalSessionFactoryBean} {@link BeanPostProcessor} uses {@link DelegatingInterceptor} wrapping the
 * associated {@link Interceptor}
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see LocalSessionFactoryBean
 * @since 1.0.0
 */
public class HibernateInterceptorBeanPostProcessor extends GenericBeanPostProcessorAdapter<LocalSessionFactoryBean>
        implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    protected void processBeforeInitialization(LocalSessionFactoryBean bean, String beanName) throws BeansException {
        Interceptor currentInterceptor = FieldUtils.getFieldValue(bean, "entityInterceptor");
        if (currentInterceptor instanceof DelegatingInterceptor) {
            return;
        }

        CompositeInterceptor compositeInterceptor = new CompositeInterceptor();
        // Add the current Interceptor
        if (currentInterceptor != null) {
            compositeInterceptor.addInterceptor(currentInterceptor);
        }

        // Add all Interceptors from Spring SPI
        List<Interceptor> interceptors = loadFactories(this.applicationContext, Interceptor.class);
        compositeInterceptor.addInterceptors(interceptors);

        // Change CompositeInterceptor
        bean.setEntityInterceptor(compositeInterceptor);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
