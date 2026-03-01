package dev.sh1on.amlethmp.common.template.service.crud;

import reactor.core.publisher.Mono;

/**
 * <b>[Service-only, Standard CRUD, Extended]</b> <br>
 * Giao diện cung cấp phương thức tìm kiếm dữ liệu duy nhất dựa trên điều kiện tìm kiếm {@link K} được chỉ định.
 *
 * @author <a href="https://github.com/henry0337">Amleth</a>
 * @param <OD> DTO riêng biệt dùng để hiển thị thông tin cho phía client
 * @param <K> Điều kiện tìm kiếm dữ liệu cho entity đang tương tác
 */
public interface ReadableWithKey<OD, K> extends Readable<OD> {
    /**
     * Tìm kiếm bản ghi được chỉ định bởi {@code key}.
     * @param key Điều kiện được sử dụng để tìm kiếm bản ghi
     * @return Bản ghi được tìm thấy nếu có, không thì trả về đối tượng {@link Mono} phát ra giá trị {@code null}.
     */
    Mono<OD> findByKey(K key);
}
