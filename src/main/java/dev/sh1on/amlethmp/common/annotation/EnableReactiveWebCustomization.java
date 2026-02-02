package dev.sh1on.amlethmp.common.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.lang.annotation.*;

/**
 * Chú thích này được sử dụng để kích hoạt các cấu hình dành riêng cho
 * <a href="https://docs.spring.io/spring-framework/reference/web/webflux.html">Spring WebFlux</a>. <br>
 * Cần kết hợp với việc triển khai giao diện {@link WebFluxConfigurer} để lớp cấu hình có thể được hoạt động đúng cách.
 * <br><br>
 * Được tạo thành bởi sự kết hợp 2 chú thích khác bao gồm {@link Configuration} và {@link EnableWebFlux} giúp ngữ cảnh
 * sử dụng rõ ràng hơn.
 * @see Configuration
 * @see EnableWebFlux
 * @see <a href="https://docs.spring.io/spring-framework/reference/web/webflux/config.html">WebFlux Config</a>
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
@Configuration
@EnableWebFlux
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableReactiveWebCustomization { }
