package dev.sh1on.amlethmp.common.config.development;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import dev.sh1on.amlethmp.common.constant.AppConstant;

/**
 * <b>[Configuration Class]</b> <br>
 * Lớp cấu hình WebFlux cho môi trường <b>phát triển (development)</b>.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @see WebFluxConfigurer
 */
@Configuration
@Profile(AppConstant.Environment.DEV)
class WebDevelopmentConfig implements WebFluxConfigurer {
    private static final int MAX_IN_MEMORY_SIZE = 1024 * 1024;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().maxInMemorySize(MAX_IN_MEMORY_SIZE);
    }
}
