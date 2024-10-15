package io.microsphere.devops.condition;

import org.springframework.context.annotation.Profile;

import java.lang.annotation.*;

/**
 * {@link Profile} for "standalone" mode
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Profile("standalone")
public @interface ConditionalOnStandaloneProfile {
}
