package dev.sh1on.amlethmp.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static dev.sh1on.amlethmp.common.shared.constant.SwaggerConstant.*;

/**
 * Lớp cấu hình cho <b>Swagger</b>.
 * @see <a href="https://swagger.io/">Swagger</a>
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
@Profile("dev")
@Configuration(proxyBeanMethods = false)
public class SwaggerConfig {

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
                        AUTHORIZATION_BUTTON_NAME,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")));

        return instance;
    }

}
