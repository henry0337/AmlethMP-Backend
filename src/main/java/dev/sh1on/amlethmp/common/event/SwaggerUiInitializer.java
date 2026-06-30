package dev.sh1on.amlethmp.common.event;

import dev.myrlennia237.component.service.I18nService;
import dev.myrlennia237.helper.ReactorHelper;
import dev.sh1on.amlethmp.common.shared.constant.AppConstant;
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
    private final ReactorHelper reactorHelper;
    private final String url;

    SwaggerUiInitializer(Environment env, I18nService i18nService, ReactorHelper reactorHelper) {
        this.i18nService = i18nService;
        this.reactorHelper = reactorHelper;
        String port = env.getProperty("server.port", "8080");
        this.url = "http://localhost:" + port + "/swagger-ui.html";
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // Đảm bảo event này chỉ được xử lý sau khi ứng dụng được khởi động đúng cách.
        if (!(event instanceof ApplicationReadyEvent)) return;

        reactorHelper.awaitSingle(Mono.fromRunnable(() -> {
            ProcessBuilder processBuilder = null;

            if (SystemUtils.IS_OS_WINDOWS) {
                processBuilder = new ProcessBuilder("cmd.exe", "/c", "start " + url);
            } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
                processBuilder = new ProcessBuilder("sh", "-c", url);
            } else {
                log.warn(i18nService.translate(AppConstant.MessageCode.OS_UNSUPPORTED));
            }

            if (processBuilder != null) {
                try {
                    processBuilder.start();
                } catch (IOException e) {
                    throw new UnsupportedOperationException();
                }
            }
        }));
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.isAssignableFrom(ApplicationReadyEvent.class);
    }
}
