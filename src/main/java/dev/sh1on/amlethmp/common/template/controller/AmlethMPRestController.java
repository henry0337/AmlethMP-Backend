package dev.sh1on.amlethmp.common.template.controller;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>[Internal, Controller-only]</b> <br>
 * Giao diện nền được tùy chỉnh dành riêng cho dự án <b>AmlethMP</b>, giúp lập trình viên khởi tạo các lớp
 * {@linkplain RestController REST API Controller} cho dự án nhanh hơn.
 *
 * @param <OD> DTO riêng biệt dùng để hiển thị thông tin cho phía client
 * @param <K>  Điều kiện tìm kiếm entity đang tương tác
 * @param <CD> DTO riêng biệt dùng để thêm mới dữ liệu
 * @param <UD> DTO riêng biệt dùng để cập nhật dữ liệu
 * @author <a href="https://github.com/henry0337">Amleth</a>
 */
@SuppressWarnings("unused")
public interface AmlethMPRestController<OD, K, CD, UD> {
    Mono<ResponseEntity<Flux<OD>>> findAll(int offset, int limit, Sort sort);

    Mono<ResponseEntity<Mono<OD>>> findByKey(K key);

    Mono<ResponseEntity<Mono<OD>>> create(CD dto);

    Mono<ResponseEntity<Mono<OD>>> update(K key, UD dto);

    Mono<ResponseEntity<Mono<Void>>> delete(K key);
}