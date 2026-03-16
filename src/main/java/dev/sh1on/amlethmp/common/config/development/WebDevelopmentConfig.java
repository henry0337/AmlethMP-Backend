package dev.sh1on.amlethmp.common.config.development;

import dev.sh1on.amlethmp.common.shared.annotation.EnableReactiveWebCustomization;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * <p>Lớp cấu hình Web cho môi trường phát triển (development).</p>
 * <p>Cấu hình các thiết lập liên quan đến <b>CORS</b> để hỗ trợ phát triển Frontend.</p>
 *
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@EnableReactiveWebCustomization
@Profile("dev")
public class WebDevelopmentConfig implements WebFluxConfigurer {

    /**
     * <p>Cấu hình <b>CORS</b> cho phép tất cả các nguồn (origins) trong môi trường phát triển.</p>
     *
     * @param registry Đối tượng đăng ký cấu hình CORS.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
