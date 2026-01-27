package dev.sh1on.amlethmp.common.utils;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ReactorUtils {

    public <T> Mono<ResponseEntity<T>> awaitableOk(@Nullable T body) {
        return Mono.just(ResponseEntity.ok(body));
    }

    public Mono<ResponseEntity<Void>> awaitableOk() {
        return Mono.just(ResponseEntity.ok().build());
    }
}
