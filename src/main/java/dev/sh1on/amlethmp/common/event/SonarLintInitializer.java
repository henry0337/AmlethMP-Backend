package dev.sh1on.amlethmp.common.event;

import dev.myrlennia237.component.service.I18nService;
import dev.sh1on.amlethmp.common.shared.constant.AppConstant;
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
            log.info(i18nService.translate(AppConstant.MessageCode.SONAR_CHECK));
            try {
                if (Boolean.TRUE.equals(isSonarLintRunning().block())) {
                    log.info(i18nService.translate(AppConstant.MessageCode.SONAR_RUNNING, new String[]{SONAR_URL}));
                    openBrowser();
                } else {
                    log.info(i18nService.translate(AppConstant.MessageCode.SONAR_NOT_RUNNING));
                }
            } catch (Exception e) {
                log.error(i18nService.translate(AppConstant.MessageCode.SONAR_ERROR));
            }
        }).block();
    }

    private Mono<Boolean> isSonarLintRunning() {
        return HttpClient.create().get()
                .uri(SonarLintInitializer.SONAR_URL)
                .responseSingle((HttpClientResponse response, ByteBufMono mono) ->
                        Mono.just(response.status().equals(HttpResponseStatus.OK)))
                .onErrorResume((Throwable e) -> {
                    log.debug("Failed to connect to SonarScanner at {}: {}", SonarLintInitializer.SONAR_URL, e.getLocalizedMessage());
                    return Mono.just(false);
                });
    }

    private void openBrowser() {
        try {
            ProcessBuilder pb;
            if (SystemUtils.IS_OS_WINDOWS) {
                String comSpec = System.getenv(AppConstant.COM_SPEC);
                pb = new ProcessBuilder(comSpec, "/c", "start", SONAR_URL);
            } else if (SystemUtils.IS_OS_MAC) {
                pb = new ProcessBuilder(AppConstant.OPEN_MACOS, SONAR_URL);
            } else if (SystemUtils.IS_OS_LINUX) {
                pb = new ProcessBuilder(AppConstant.OPEN_LINUX, SONAR_URL);
            } else {
                log.warn(i18nService.translate(AppConstant.MessageCode.OS_UNSUPPORTED));
                return;
            }
            pb.start();
        } catch (IOException | RuntimeException e) {
            log.error(i18nService.translate(AppConstant.MessageCode.BROWSER_OPEN_ERROR), e);
        }
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.isAssignableFrom(ApplicationReadyEvent.class);
    }
}
