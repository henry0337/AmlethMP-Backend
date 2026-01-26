package dev.sh1on.amlethmp.common.template.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>[Adapter-only]</b> <br>
 * Giao diện phía {@link RestController Controller} cung cấp các phương thức CRUD tối thiểu cần thiết cho một Entity.
 * @author <a href="https://github.com/henry0337">Amleth</a>
 * @param <OD> DTO riêng biệt dùng để hiển thị thông tin cho phía client
 * @param <K> Điều kiện tìm kiếm entity đang tương tác
 * @param <CD> DTO riêng biệt dùng để thêm mới dữ liệu
 * @param <UD> DTO riêng biệt dùng để cập nhật dữ liệu
 */
@SuppressWarnings("unused")
public interface AmlethMPController<OD, K, CD, UD> {
    Mono<ResponseEntity<Flux<OD>>> findAll(Pageable pageable, Sort sort);
    Mono<ResponseEntity<Mono<OD>>> obtainByKey(K key);
    Mono<ResponseEntity<Mono<OD>>> create(CD dto);
    Mono<ResponseEntity<Mono<OD>>> update(K key, UD dto);
    Mono<ResponseEntity<Mono<Void>>> delete(K key);
}
