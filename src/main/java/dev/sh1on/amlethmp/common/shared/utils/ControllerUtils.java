package dev.sh1on.amlethmp.common.shared.utils;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Component
@Slf4j
public class ControllerUtils {
    public <T> Mono<ResponseEntity<T>> awaitOk(@Nullable T body) {
        return Mono.just(ResponseEntity.ok(body));
    }

    public <T> Mono<ResponseEntity<T>> awaitCreated(@Nullable T body) {
        try {
            return Mono.just(ResponseEntity.created(new URI("")).body(body));
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
            return Mono.just(ResponseEntity.ofNullable(null));
        }
    }
}
