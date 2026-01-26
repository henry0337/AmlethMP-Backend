package dev.sh1on.amlethmp.common.template.service;

import reactor.core.publisher.Mono;

/**
 * <b>[Service-only, Standard CRUD]</b> <br>
 * Giao diện hàm cung cấp khả năng <b>xóa</b> dữ liệu thuộc tác vụ CRUD tiêu chuẩn.
 *
 * @param <K> Điều kiện tìm kiếm dữ liệu cho entity đang tương tác
 * @author <a href="https://github.com/henry0337">Amleth</a>
 */
@FunctionalInterface
public interface Deletable<K> {

    /**
     * Xóa một bản ghi dựa trên {@code id} được chỉ định.
     * @param key Điều kiện tìm kiếm dữ liệu muốn xóa.
     */
    Mono<Void> deleteById(K key);
}
