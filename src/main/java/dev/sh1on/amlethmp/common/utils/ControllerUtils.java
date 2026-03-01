package dev.sh1on.amlethmp.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerUtils {
    public static <T> Mono<ResponseEntity<T>> awaitOk(@Nullable T body) {
        return Mono.just(ResponseEntity.ok(body));
    }

    public static <T> Mono<ResponseEntity<T>> awaitCreated(@Nullable T body) {
        return Mono.just(new ResponseEntity<>(body, HttpStatus.CREATED));
    }
}
