package dev.sh1on.amlethmp.common.config.production;

import dev.sh1on.amlethmp.common.shared.annotation.EnableReactiveWebCustomization;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Lớp cấu hình WebFlux dành riêng cho môi trường <b>Production</b>.
 * <p>
 * Lớp này kích hoạt các tùy chỉnh Web đặc thù thông qua annotation {@link EnableReactiveWebCustomization}
 * và cấu hình các thành phần xử lý yêu cầu HTTP để đảm bảo tính ổn định và bảo mật trong môi trường thực tế.
 * </p>
 *
 * @see WebFluxConfigurer
 * @see EnableReactiveWebCustomization
 */
@Profile("prod")
@EnableReactiveWebCustomization
public class WebProductionConfig implements WebFluxConfigurer { }
