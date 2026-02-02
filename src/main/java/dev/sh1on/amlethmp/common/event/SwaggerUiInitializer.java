package dev.sh1on.amlethmp.common.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Stella</a>
 */
@Profile("dev")
@Component
public class SwaggerUiInitializer implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // TODO: Thêm logic tự động khởi động Swagger UI khi dự án đã khởi động xong
    }
}
