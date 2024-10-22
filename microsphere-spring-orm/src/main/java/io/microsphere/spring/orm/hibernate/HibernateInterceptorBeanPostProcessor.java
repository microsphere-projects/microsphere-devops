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
import io.microsphere.spring.beans.factory.config.GenericBeanPostProcessorAdapter;
import org.hibernate.Interceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import java.util.ArrayList;
import java.util.List;

import static io.microsphere.reflect.FieldUtils.getFieldValue;
import static io.microsphere.spring.util.BeanFactoryUtils.getBeans;
import static io.microsphere.spring.util.BeanUtils.getBeanNames;
import static io.microsphere.spring.util.SpringFactoriesLoaderUtils.loadFactories;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

/**
 * The {@link LocalSessionFactoryBean LocalSessionFactoryBean's} {@link BeanPostProcessor} composites all
 * {@link Interceptor Interceptors} from {@link LocalSessionFactoryBean#setEntityInterceptor(Interceptor) the existed},
 * Spring SPI and Spring Beans.
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see LocalSessionFactoryBean
 * @see CompositeInterceptor
 * @since 1.0.0
 */
public class HibernateInterceptorBeanPostProcessor extends GenericBeanPostProcessorAdapter<LocalSessionFactoryBean>
        implements ApplicationContextAware {

    private static final Class<Interceptor> INTERCEPTOR_CLASS = Interceptor.class;

    private ApplicationContext applicationContext;

    @Override
    protected void processBeforeInitialization(LocalSessionFactoryBean bean, String beanName) throws BeansException {

        final List<Interceptor> existedInterceptors = getExistedInterceptors(bean);

        // Load all Interceptors from Spring SPI
        List<Interceptor> interceptors = loadFactories(this.applicationContext, INTERCEPTOR_CLASS);

        // Load all Interceptors from Spring Beans
        List<Interceptor> interceptorBeans = loadInterceptorBeans();

        int size = existedInterceptors.size() + interceptors.size() + interceptorBeans.size();

        if (size < 1) {
            return;
        }

        List<Interceptor> allInterceptors = new ArrayList<>(size);

        // Add existed Interceptors from Spring SPI
        allInterceptors.addAll(existedInterceptors);

        // Add all Interceptors from Spring SPI
        allInterceptors.addAll(interceptors);

        // Add all Interceptor Beans
        allInterceptors.addAll(interceptorBeans);

        final Interceptor newInterceptor;

        if (allInterceptors.size() == 1) {
            newInterceptor = allInterceptors.get(0);
        } else {
            // Sort all Interceptors
            AnnotationAwareOrderComparator.sort(allInterceptors);
            newInterceptor = new CompositeInterceptor(allInterceptors);
        }

        // Change
        bean.setEntityInterceptor(newInterceptor);
    }

    private List<Interceptor> getExistedInterceptors(LocalSessionFactoryBean bean) {
        Interceptor currentInterceptor = getCurrentInterceptor(bean);

        if (currentInterceptor instanceof CompositeInterceptor) {
            return ((CompositeInterceptor) currentInterceptor).getInterceptors();
        } else if (currentInterceptor != null) {
            return singletonList(currentInterceptor);
        }
        return emptyList();
    }

    private List<Interceptor> loadInterceptorBeans() {
        String[] beanNames = getBeanNames(this.applicationContext, INTERCEPTOR_CLASS, true);
        return getBeans(this.applicationContext, beanNames, INTERCEPTOR_CLASS);
    }

    private Interceptor getCurrentInterceptor(LocalSessionFactoryBean bean) {
        Interceptor currentInterceptor = null;
        try {
            currentInterceptor = getFieldValue(bean, "entityInterceptor");
        } catch (Throwable ignored) {
        }
        return currentInterceptor;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
