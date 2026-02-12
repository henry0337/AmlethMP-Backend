package dev.sh1on.amlethmp.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerUtils {
    public static <T> Mono<ResponseEntity<T>> awaitableOk(@Nullable T body) {
        return Mono.just(ResponseEntity.ok(body));
    }

    public static <T> Mono<ResponseEntity<T>> awaitableCreated(@Nullable T body) {
        return Mono.just(new ResponseEntity<>(body, HttpStatus.CREATED));
    }
}
