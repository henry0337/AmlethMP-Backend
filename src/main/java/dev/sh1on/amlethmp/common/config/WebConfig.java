package dev.sh1on.amlethmp.common.config;

import dev.sh1on.amlethmp.common.annotation.EnableReactiveWebConfiguration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
@EnableReactiveWebConfiguration
public class WebConfig implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
