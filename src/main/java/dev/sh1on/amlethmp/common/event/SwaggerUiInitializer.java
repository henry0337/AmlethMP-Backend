package dev.sh1on.amlethmp.common.event;

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
@Profile(AppConstant.Environment.DEV)
@Order(3)
@Slf4j
class SwaggerUiInitializer implements GenericApplicationListener {
    private final ReactorHelper reactor;
    private final String url;

    SwaggerUiInitializer(Environment env, ReactorHelper reactor) {
        String port = env.getProperty("server.port", "8080");
        this.url = "http://localhost:" + port + "/swagger-ui.html";
        this.reactor = reactor;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        var runnable = Mono.fromRunnable(this::openSwaggerUi);
        reactor.waitUntilCompleted(runnable);
    }

    private void openSwaggerUi() {
        try {
            ProcessBuilder pb;
            if (SystemUtils.IS_OS_WINDOWS) {
                String comSpec = System.getenv(AppConstant.COM_SPEC);
                pb = new ProcessBuilder(comSpec, "/c", "start", url);
            } else if (SystemUtils.IS_OS_MAC) {
                pb = new ProcessBuilder(AppConstant.OPEN_MACOS, url);
            } else if (SystemUtils.IS_OS_LINUX) {
                pb = new ProcessBuilder(AppConstant.OPEN_LINUX, url);
            } else {
                log.warn("Your current OS you are using is not supported by this backend, please use other supporting OSes.");
                return;
            }
            pb.start();
        } catch (IOException | RuntimeException e) {
            log.error("Error opening browser for SonarScanner URL:", e);
        }
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.isAssignableFrom(ApplicationReadyEvent.class);
    }
}
