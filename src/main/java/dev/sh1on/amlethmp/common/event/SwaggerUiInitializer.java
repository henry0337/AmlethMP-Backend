package dev.sh1on.amlethmp.common.event;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Profile("dev")
@Component
@RequiredArgsConstructor
public class SwaggerUiInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final Environment env;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String port = env.getProperty("server.port", "8080");
        String url = "http://localhost:" + port + "/swagger-ui.html";

        Mono.fromRunnable(() -> {

        });
    }
}
