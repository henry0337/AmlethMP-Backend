package dev.sh1on.amlethmp.common.event;

import java.io.IOException;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import dev.myrlennia237.helper.ReactorHelper;
import dev.myrlennia237.service.ReactiveHttpClient;
import dev.sh1on.amlethmp.common.shared.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import reactor.core.publisher.Mono;

/**
 * Component giúp khởi tạo và chạy <b>Swagger UI</b>.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Component
@Profile(AppConstant.Environment.DEV)
@Order(3)
@Slf4j
class SwaggerUiInitializer implements GenericApplicationListener {
    private final ReactiveHttpClient reactiveHttpClient;
    private final ReactorHelper reactor;
    private final String url;

    SwaggerUiInitializer(Environment env, ReactorHelper reactor, ReactiveHttpClient reactiveHttpClient) {
        this.reactor = reactor;
        this.reactiveHttpClient = reactiveHttpClient;
        this.url = createUrl(env.getProperty("server.port", "8080"));
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        var runnable = Mono.fromRunnable(this::openSwaggerUi);
        reactor.waitUntilCompleted(runnable);
    }

    private void openSwaggerUi() {
        log.info("Checking if Swagger UI is available...");
        try {
            boolean isAvailable = Boolean.TRUE.equals(reactor.waitUntilCompleted(isSwaggerUiAvailable()));
            if (!isAvailable) {
                log.info("Swagger UI is not available at {}", url);
                return;
            }
            log.info("Swagger UI is available at {}", url);

            if (System.getProperty(AppConstant.SWAGGER_UI_OPENED_PROPERTY) != null) {
                log.info("Swagger UI đã được mở trong phiên JVM này, bỏ qua để không mở thêm tab mới.");
                return;
            }

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
            System.setProperty(AppConstant.SWAGGER_UI_OPENED_PROPERTY, "true");
        } catch (IOException | RuntimeException e) {
            log.error("Error opening browser for Swagger UI URL:", e);
        }
    }

    private Mono<Boolean> isSwaggerUiAvailable() {
        return reactiveHttpClient.doGet(url, new ParameterizedTypeReference<String>() { })
                .map(response -> true)
                .defaultIfEmpty(true)
                .onErrorResume((Throwable e) -> {
                    log.debug("Failed to connect to Swagger UI at {}: {}", url, e.getLocalizedMessage());
                    return Mono.just(false);
                });
    }

    private static String createUrl(String port) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port)
                .path("/swagger-ui.html")
                .toUriString();
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.isAssignableFrom(ApplicationReadyEvent.class);
    }
}
