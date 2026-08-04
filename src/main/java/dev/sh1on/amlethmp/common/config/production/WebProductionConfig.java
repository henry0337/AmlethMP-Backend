package dev.sh1on.amlethmp.common.config.production;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import dev.sh1on.amlethmp.common.shared.constant.AppConstant;

/**
 * Lớp cấu hình WebFlux dành riêng cho môi trường <b>Production</b>.
 *
 * @see WebFluxConfigurer
 */
@Configuration
@Profile(AppConstant.Environment.PROD)
class WebProductionConfig implements WebFluxConfigurer { }
