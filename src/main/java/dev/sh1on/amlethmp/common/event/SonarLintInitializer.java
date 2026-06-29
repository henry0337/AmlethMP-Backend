package dev.sh1on.amlethmp.common.event;

import dev.myrlennia237.component.service.I18nService;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufMono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

import java.io.IOException;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Component
@Profile("dev")
@Order(4)
@RequiredArgsConstructor
@Slf4j
class SonarLintInitializer implements GenericApplicationListener {
    private static final String SONAR_URL = "http://localhost:9000";

    private final I18nService i18nService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Mono.fromRunnable(() -> {
            log.info(i18nService.translate("sonar.check"));
            try {
                if (Boolean.TRUE.equals(isSonarLintRunning().block())) {
                    log.info(i18nService.translate("sonar.running", new Object[]{SONAR_URL}));
                    openBrowser();
                } else {
                    log.info(i18nService.translate("sonar.not_running"));
                }
            } catch (Exception _) {
                log.error(i18nService.translate("sonar.error"));
            }
        }).block();
    }

    private Mono<Boolean> isSonarLintRunning() {
        return HttpClient.create().get()
                .uri(SonarLintInitializer.SONAR_URL)
                .responseSingle((HttpClientResponse response, ByteBufMono _) ->
                        Mono.just(response.status().equals(HttpResponseStatus.OK)))
                .onErrorResume((Throwable e) -> {
                    log.debug("Failed to connect to SonarScanner at {}: {}", SonarLintInitializer.SONAR_URL, e.getLocalizedMessage());
                    return Mono.just(false);
                });
    }

    private void openBrowser() {
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                new ProcessBuilder("cmd.exe", "/c", "start " + SonarLintInitializer.SONAR_URL).start();
            } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
                new ProcessBuilder("xdg-open", SonarLintInitializer.SONAR_URL).start();
            } else {
                log.warn(i18nService.translate("os.unsupported"));
            }
        } catch (IOException e) {
            log.error(i18nService.translate("browser.open.error"), e);
        }
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.isAssignableFrom(ApplicationReadyEvent.class);
    }
}
