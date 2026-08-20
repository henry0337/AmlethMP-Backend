package dev.sh1on.amlethmp.common.event;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import dev.myrlennia237.component.event.ReadyEventListener;
import dev.myrlennia237.component.service.I18nService;
import dev.myrlennia237.helper.ReactorHelper;
import dev.myrlennia237.helper.WebFluxUriBuilder;
import dev.sh1on.amlethmp.common.constant.AmlethMPEndpoint;
import dev.sh1on.amlethmp.common.constant.AppConstant;
import dev.sh1on.amlethmp.common.constant.InitializationPriority;
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
    private final I18nService i18nService;
    private final String url;

    SwaggerUiInitializer(Environment env, ReactorHelper reactor, I18nService i18nService) {
        this.url = createUrl(env.getProperty("server.port", Integer.class, 8080));
        this.reactor = reactor;
        this.i18nService = i18nService;
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
                log.warn(i18nService.translate("swagger.os.notsupported"));
                return;
            }
            pb.start();
            log.info(i18nService.translate("swagger.available", url));
        } catch (IOException | RuntimeException e) {
            log.error(i18nService.translate("swagger.error"), e);
        }
    }
}
