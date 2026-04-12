package dev.sh1on.amlethmp.common.event;

import dev.sh1on.amlethmp.common.shared.utils.I18NUtils;
import dev.sh1on.amlethmp.common.shared.utils.ReactorUtils;
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
import reactor.netty.http.client.HttpClient;

import java.io.IOException;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Component
@Profile("dev")
@Order(4)
@RequiredArgsConstructor
@Slf4j
public class SonarLintInitializer implements GenericApplicationListener {
    private static final String SONAR_URL = "http://localhost:9000";

    private final I18NUtils i18NUtils;
    private final ReactorUtils reactorUtils;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (!(event instanceof ApplicationReadyEvent)) return;

        reactorUtils.awaitMono(Mono.fromRunnable(() -> {
            log.info(i18NUtils.translateMessage("sonar.check"));
            try {
                if (Boolean.TRUE.equals(reactorUtils.awaitMono(isSonarLintRunning()))) {
                    log.info(i18NUtils.translateDynamicMessage("sonar.running", SONAR_URL));
                    openBrowser();
                } else {
                    log.info(i18NUtils.translateMessage("sonar.not_running"));
                }
            } catch (Exception e) {
                log.error(i18NUtils.translateMessage("sonar.error"), e);
            }
        }));
    }

    private Mono<Boolean> isSonarLintRunning() {
        return HttpClient.create()
                .get()
                .uri(SonarLintInitializer.SONAR_URL)
                .responseSingle((response, bytes) -> Mono.just(response.status().equals(HttpResponseStatus.OK)))
                .onErrorResume(e -> {
                    log.debug("Failed to connect to SonarScanner at {}: {}", SonarLintInitializer.SONAR_URL, e.getLocalizedMessage());
                    return reactorUtils.single(false);
                });
    }

    private void openBrowser() {
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                new ProcessBuilder("cmd.exe", "/c", "start " + SonarLintInitializer.SONAR_URL).start();
            } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
                new ProcessBuilder("sh", "-c", "xdg-open " + SonarLintInitializer.SONAR_URL).start(); // xdg-open for Linux, 'open' for Mac
            } else {
                log.warn(i18NUtils.translateMessage("os.unsupported"));
            }
        } catch (IOException e) {
            log.error(i18NUtils.translateMessage("browser.open.error"), e);
        }
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.isAssignableFrom(ApplicationReadyEvent.class);
    }
}
