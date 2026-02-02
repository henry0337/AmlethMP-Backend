package dev.sh1on.amlethmp.common.utils;

import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ControllerUtils {
    public <T> Mono<ResponseEntity<T>> awaitableOk(@Nullable T body) {
        return Mono.just(ResponseEntity.ok(body));
    }

    public <T> Mono<ResponseEntity<T>> awaitableCreated(@Nullable T body) {
        return Mono.just(new ResponseEntity<>(body, HttpStatus.CREATED));
    }
}
