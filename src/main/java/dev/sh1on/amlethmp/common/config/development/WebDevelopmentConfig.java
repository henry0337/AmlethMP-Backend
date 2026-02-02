package dev.sh1on.amlethmp.common.config.development;

import dev.sh1on.amlethmp.common.annotation.EnableReactiveWebCustomization;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Stella</a>
 */
@Profile("dev")
@EnableReactiveWebCustomization
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
