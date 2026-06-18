package dev.sh1on.amlethmp.common.shared.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation tiện ích kết hợp {@link Configuration @Configuration} và {@link EnableWebFluxSecurity @EnableWebFluxSecurity}
 * để kích hoạt và thiết lập các cấu hình bảo mật tùy chỉnh cho ứng dụng Spring Boot trong môi trường <b>Reactive</b>.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Configuration
@EnableWebFluxSecurity
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableReactiveSecurityCustomization { }
