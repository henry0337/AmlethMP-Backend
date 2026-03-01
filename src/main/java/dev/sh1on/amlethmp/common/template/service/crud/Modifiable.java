package dev.sh1on.amlethmp.common.template.service.crud;

import reactor.core.publisher.Mono;

/**
 * <b>[Service-only, Standard CRUD]</b> <br>
 * Giao diện hàm cung cấp khả năng <b>cập nhật dữ liệu</b> mới cho dữ liệu đang tồn tại.
 *
 * @author <a href="https://github.com/henry0337">Amleth</a>
 * @param <OD> DTO riêng biệt dùng để hiển thị thông tin cho phía client
 * @param <K> Điều kiện tìm kiếm dữ liệu cho entity đang tương tác
 * @param <UD> DTO riêng biệt dùng để cập nhật dữ liệu
 */
@FunctionalInterface
public interface Modifiable<OD, K, UD> {

    /**
     * Cập nhật dữ liệu mới cho bản ghi được tìm thấy bởi {@code key}, nếu có.
     * @param key Điều kiện tìm kiếm bản ghi
     * @param dto Dữ liệu mới dùng để cập nhật
     * @return Bản ghi được tìm thấy với dữ liệu đã được cập nhật.
     */
    Mono<OD> update(K key, UD dto);
}
