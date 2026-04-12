package dev.sh1on.amlethmp.common.shared.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

import java.lang.annotation.*;

/**
 * Annotation tiện ích kết hợp {@link Configuration @Configuration} và {@link EnableWebFluxSecurity @EnableWebFluxSecurity}
 * để kích hoạt và thiết lập các cấu hình bảo mật tùy chỉnh cho ứng dụng Spring Boot trong môi trường <b>Reactive</b>.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Configuration
@EnableWebFluxSecurity
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableReactiveSecurityCustomization { }
