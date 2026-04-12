package dev.sh1on.amlethmp.common.shared.utils;

import dev.sh1on.amlethmp.common.config.ServerWebExchangeContextFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * Lớp chứa các phương thức giúp wrap kiểu {@code Mono<ResponseEntity<T>>} cho các phương thức trong Controller.
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ControllerUtils {

    /**
     * Trả về phản hồi 200 OK được bao bọc trong một Mono.
     *
     * @param source Luồng dữ liệu cần trả về
     * @param <T>    Kiểu dữ liệu của body
     * @return {@link Mono} chứa {@link ResponseEntity}
     */
    public <T> Mono<ResponseEntity<T>> awaitOk(Mono<T> source) {
        return source.map(ResponseEntity::ok);
    }

    /**
     * Trả về phản hồi 201 Created.
     *
     * @param source Luồng dữ liệu cần trả về sau khi tạo
     * @param <T>    Kiểu dữ liệu của body
     * @return {@link Mono} chứa {@link ResponseEntity}
     */
    public <T> Mono<ResponseEntity<T>> awaitCreated(Mono<T> source) {
        return ServerWebExchangeContextFilter.getExchange()
                .flatMap(exchange ->
                        source.map(body -> ResponseEntity.created(exchange.getRequest().getURI()).body(body)))
                .switchIfEmpty(source.map(body -> ResponseEntity.status(201).body(body)));
    }

    /**
     * Trả về phản hồi 204 No Content.
     *
     * @param source Luồng xử lý (thường là Mono<Void>)
     * @return {@link Mono} chứa {@link ResponseEntity} trống
     */
    public Mono<ResponseEntity<Void>> awaitNoContent(Mono<Void> source) {
        return source.then(Mono.just(ResponseEntity.noContent().build()));
    }

    /**
     * Trả về 200 OK nếu có dữ liệu, ngược lại trả về 404 Not Found nếu Mono trống.
     *
     * @param source Luồng dữ liệu cần trả về
     * @param <T>    Kiểu dữ liệu của body
     * @return {@link Mono} chứa {@link ResponseEntity}
     */
    public <T> Mono<ResponseEntity<T>> awaitOrNotFound(Mono<T> source) {
        return source.map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
