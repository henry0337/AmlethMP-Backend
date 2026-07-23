package dev.sh1on.amlethmp.common.event;

import dev.myrlennia237.service.ReactiveHttpClient;
import dev.sh1on.amlethmp.common.shared.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Component
@Profile(AppConstant.Environment.DEV)
@Order(4)
@Slf4j
@RequiredArgsConstructor
class SonarLintInitializer implements GenericApplicationListener {
    private static final String SONAR_URL = "http://localhost:9000";

    private final ReactiveHttpClient reactiveHttpClient;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        Mono.fromRunnable(this::initSonarLint).block();
    }

    private void initSonarLint() {
        log.info("Checking if SonarScanner is running...");
        try {
            if (Boolean.TRUE.equals(isSonarLintRunning().block())) {
                log.info("SonarScanner is running at {}", SONAR_URL);

                ProcessBuilder pb;
                if (SystemUtils.IS_OS_WINDOWS) {
                    String comSpec = System.getenv(AppConstant.COM_SPEC);
                    pb = new ProcessBuilder(comSpec, "/c", "start", SONAR_URL);
                } else if (SystemUtils.IS_OS_MAC) {
                    pb = new ProcessBuilder(AppConstant.OPEN_MACOS, SONAR_URL);
                } else if (SystemUtils.IS_OS_LINUX) {
                    pb = new ProcessBuilder(AppConstant.OPEN_LINUX, SONAR_URL);
                } else {
                    log.warn("Your current OS you are using is not supported by this backend, please use other supporting OSes.");
                    return;
                }
                pb.start();

            } else {
                log.info("SonarScanner is not running.");
            }
        } catch (IOException | RuntimeException e) {
            log.error("Error while checking or opening SonarScanner URL:" + e.getLocalizedMessage());
        }
    }

    private Mono<Boolean> isSonarLintRunning() {
        return reactiveHttpClient.doGet(SonarLintInitializer.SONAR_URL, new ParameterizedTypeReference<String>() { })
                .map(response -> true)
                .defaultIfEmpty(true)
                .onErrorResume((Throwable e) -> {
                    log.debug("Failed to connect to SonarScanner at {}: {}", SonarLintInitializer.SONAR_URL, e.getLocalizedMessage());
                    return Mono.just(false);
                });
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.isAssignableFrom(ApplicationReadyEvent.class);
    }
}
