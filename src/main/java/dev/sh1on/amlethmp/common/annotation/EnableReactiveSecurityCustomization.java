package dev.sh1on.amlethmp.common.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

import java.lang.annotation.*;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Configuration
@EnableWebFluxSecurity
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableReactiveSecurityCustomization { }
