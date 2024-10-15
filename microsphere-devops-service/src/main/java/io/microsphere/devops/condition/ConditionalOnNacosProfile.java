package io.microsphere.devops.condition;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

/**
 * {@link Profile} for "nacos"
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Profile("nacos")
public @interface ConditionalOnNacosProfile {
}
