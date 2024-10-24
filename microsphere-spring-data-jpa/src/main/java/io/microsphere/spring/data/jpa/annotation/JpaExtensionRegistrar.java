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

import io.microsphere.spring.context.annotation.BeanCapableImportCandidate;
import io.microsphere.spring.data.jpa.event.EntityLifecycleApplicationListener;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import static io.microsphere.spring.util.BeanRegistrar.registerBeanDefinition;

/**
 * The {@link BeanDefinition} Registrar for JPA Extension
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EnableJpaExtension
 * @see BeanCapableImportCandidate
 * @see ImportBeanDefinitionRegistrar
 * @since 1.0.0
 */
class JpaExtensionRegistrar extends BeanCapableImportCandidate implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerEntityLifecycleApplicationListener(registry);
    }

    private void registerEntityLifecycleApplicationListener(BeanDefinitionRegistry registry) {
        registerBeanDefinition(registry, EntityLifecycleApplicationListener.class);
    }

}
