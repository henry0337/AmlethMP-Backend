package dev.sh1on.amlethmp.common.config.development;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import dev.sh1on.amlethmp.common.constant.AppConstant;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * <b>[Configuration Class]</b> <br>
 * Lớp cấu hình <b>Swagger/OpenAPI</b>.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @see <a href="https://swagger.io/">Swagger</a>
 */
@Configuration(proxyBeanMethods = false)
@Profile(AppConstant.Environment.DEV)
class SwaggerConfig {
    private static final String OPENAPI_VERSION = "3.2.0";
    private static final String OPENAPI_TITLE = "AmlethMP";
    private static final String OPENAPI_DESCRIPTION = "Danh sách các endpoint API của hệ thống AmlethMP";
    private static final String API_VERSION = "0.1.0-SNAPSHOT";
    private static final String AUTHORIZATION_TYPE = "Bearer Token";

    @Bean
    OpenAPI swagger() {
        var info = new Info();
        info.setTitle(OPENAPI_TITLE);
        info.setDescription(OPENAPI_DESCRIPTION);
        info.setVersion(API_VERSION);

        var securityScheme = new SecurityScheme();
        securityScheme.setType(SecurityScheme.Type.HTTP);
        securityScheme.setScheme("bearer");
        securityScheme.setBearerFormat("JWT");

        var component = new Components();
        component.addSecuritySchemes(AUTHORIZATION_TYPE, securityScheme);

        var swagger = new OpenAPI();
        swagger.setOpenapi(OPENAPI_VERSION);
        swagger.setInfo(info);
        swagger.setComponents(component);
        return swagger;
    }
}
