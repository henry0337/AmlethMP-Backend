package dev.sh1on.amlethmp.common.event;

import dev.myrlennia237.component.service.I18nService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Component
@Order(3)
@Profile("dev")
@Slf4j
class SwaggerUiInitializer implements GenericApplicationListener {
    private final I18nService i18nService;
    private final String url;

    SwaggerUiInitializer(Environment env, I18nService i18nService) {
        this.i18nService = i18nService;
        String port = env.getProperty("server.port", "8080");
        this.url = "http://localhost:" + port + "/swagger-ui.html";
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // Đảm bảo event này chỉ được xử lý sau khi ứng dụng được khởi động đúng cách.
        if (!(event instanceof ApplicationReadyEvent)) return;

        // Ghi chú: Nếu bạn (người đọc mã) mà thấy phương thức markAsSynchronous bị đánh dấu là deprecated, yên tâm vì nó
        // là chủ đích của tôi thôi, đọc Javadoc của phương thức là sẽ rõ
        Mono.fromRunnable(() -> {
            ProcessBuilder processBuilder = null;

            if (SystemUtils.IS_OS_WINDOWS) {
                processBuilder = new ProcessBuilder("cmd.exe", "/c", "start " + url);
            } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
                processBuilder = new ProcessBuilder("sh", "-c", url);
            } else {
                log.warn(i18nService.translate("os.unsupported"));
            }

            if (processBuilder != null) {
                try {
                    processBuilder.start();
                } catch (IOException e) {
                    throw new UnsupportedOperationException(e);
                }
            }
        }).block();
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.isAssignableFrom(ApplicationReadyEvent.class);
    }
}
