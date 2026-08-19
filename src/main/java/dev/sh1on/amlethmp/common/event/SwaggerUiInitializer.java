package dev.sh1on.amlethmp.common.event;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import dev.myrlennia237.component.event.ReadyEventListener;
import dev.myrlennia237.helper.ReactorHelper;
import dev.myrlennia237.helper.WebFluxUriBuilder;
import dev.sh1on.amlethmp.common.constant.AmlethMPEndpoint;
import dev.sh1on.amlethmp.common.constant.AppConstant;
import dev.sh1on.amlethmp.common.enums.InitializationPriority;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import reactor.core.publisher.Mono;

/**
 * <b>[Lifecycle Event]</b> <br>
 * Component giúp khởi tạo và chạy <b>Swagger UI</b>.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Component
@Profile(AppConstant.Environment.DEV)
@Order(InitializationPriority.THIRD)
@Slf4j
class SwaggerUiInitializer implements ReadyEventListener {
    private final ReactorHelper reactor;
    private final String url;

    SwaggerUiInitializer(Environment env, ReactorHelper reactor) {
        this.reactor = reactor;
        this.url = createUrl(env.getProperty("server.port", Integer.class, 8080));
    }

    private static String createUrl(int port) {
        return WebFluxUriBuilder.build("http", "localhost", port, AmlethMPEndpoint.Docs.SWAGGER_UI_HTML);
    }

    @Override
    public void onApplicationReady(ApplicationReadyEvent event) {
        var runnable = Mono.fromRunnable(this::openSwaggerUi);
        reactor.emitCompleteSignal(runnable);
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
            log.error("Error opening browser for Swagger UI URL:", e);
        }
    }
}
