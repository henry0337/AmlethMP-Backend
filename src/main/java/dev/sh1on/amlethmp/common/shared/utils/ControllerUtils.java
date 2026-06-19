package dev.sh1on.amlethmp.common.shared.utils;

import dev.sh1on.amlethmp.common.config.ServerWebExchangeContextFilter;
import dev.sh1on.amlethmp.common.shared.dto.PagedResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
     * Trả về phản hồi 200 OK với body rỗng sau khi luồng {@code Mono<Void>} hoàn tất.
     *
     * @param source Luồng xử lý (thường là Mono<Void>)
     * @return {@link Mono} chứa {@link ResponseEntity} 200 OK không có body
     */
    public Mono<ResponseEntity<Void>> awaitOkEmpty(Mono<Void> source) {
        return source.then(Mono.just(ResponseEntity.ok().build()));
    }

    /**
     * Trả về phản hồi 200 OK với dữ liệu phân trang đã được rút gọn thành {@link PagedResponse}.
     *
     * @param source Luồng dữ liệu chứa {@link Page} cần trả về
     * @param <T>    Kiểu dữ liệu của mỗi bản ghi
     * @return {@link Mono} chứa {@link ResponseEntity} bọc {@link PagedResponse}
     */
    public <T> Mono<ResponseEntity<PagedResponse<T>>> awaitPaged(Mono<Page<T>> source) {
        return source.map(page -> ResponseEntity.ok(PagedResponse.from(page)));
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
