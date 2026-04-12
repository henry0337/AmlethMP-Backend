package dev.sh1on.amlethmp.common.config.development;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * <p>Lớp cấu hình Web cho môi trường phát triển (development).</p>
 *
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Configuration
@Profile("dev")
public class WebDevelopmentConfig implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
