package dev.sh1on.amlethmp.common.event;

import dev.sh1on.amlethmp.common.utils.MessageUtils;
import dev.sh1on.amlethmp.common.utils.ReactorUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class SwaggerUiInitializer implements GenericApplicationListener {
    private final Environment env;
    private final MessageUtils messageUtils;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // Đảm bảo event này chỉ được xử lý sau khi ứng dụng được khởi động đúng cách.
        if (!(event instanceof ApplicationReadyEvent)) return;

        String port = env.getProperty("server.port", "8080");
        String url = "http://localhost:" + port + "/swagger-ui.html";

        // Ghi chú: Nếu bạn (người đọc mã) mà thấy phương thức markAsSynchronous bị đánh dấu là deprecated, yên tâm vì nó
        // là chủ đích của tôi thôi, đọc Javadoc của phương thức là sẽ rõ
        ReactorUtils.markAsSynchronous(Mono.fromRunnable(() -> {
            ProcessBuilder processBuilder = null;

            if (SystemUtils.IS_OS_WINDOWS) {
                processBuilder = new ProcessBuilder("cmd.exe", "/c", "start " + url);
            } else if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
                processBuilder = new ProcessBuilder("sh", "-c", url);
            } else {
                log.warn(messageUtils.obtainStaticLocalizedMessage("os.unsupported"));
            }

            try {
                if (processBuilder != null) processBuilder.start();
            } catch (IOException e) {
                throw new UnsupportedOperationException(e);
            }
        }).subscribeOn(Schedulers.boundedElastic()));
    }

    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return eventType.isAssignableFrom(ApplicationReadyEvent.class);
    }
}
