package dev.sh1on.amlethmp.common.config.development;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import dev.sh1on.amlethmp.common.shared.constant.AppConstant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * <p>Lớp cấu hình <b>Swagger/OpenAPI</b>.</p>
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @see <a href="https://swagger.io/">Swagger</a>
 */
@Configuration(proxyBeanMethods = false)
@Profile(AppConstant.Environment.DEV)
class SwaggerConfig {
    private static final String OPENAPI_VERSION = "3.1.1";
    private static final String OPENAPI_TITLE = "AmlethMP Backend";
    private static final String OPENAPI_DESCRIPTION = "Chứa thông tin liên quan tới các API của dự án";
    private static final String API_VERSION = "0.1.0-SNAPSHOT";
    private static final String AUTHORIZATION_TYPE = "Bearer Token";

    @Bean
    OpenAPI swagger() {
        var instance = new OpenAPI();
        instance.setOpenapi(OPENAPI_VERSION);
        instance.setInfo(new Info()
                .title(OPENAPI_TITLE)
                .version(API_VERSION)
                .description(OPENAPI_DESCRIPTION));
        instance.setComponents(new Components()
                .addSecuritySchemes(
                        AUTHORIZATION_TYPE,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")));

        return instance;
    }
}
