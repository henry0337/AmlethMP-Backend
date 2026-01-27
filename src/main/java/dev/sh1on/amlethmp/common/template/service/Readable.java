package dev.sh1on.amlethmp.common.template.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

/**
 * <b>[Service-only, Standard CRUD]</b> <br>
 * Giao diện hàm cung cấp khả năng <b>đọc</b> dữ liệu thuộc tác vụ CRUD tiêu chuẩn.
 *
 * @author <a href="https://github.com/henry0337">Amleth</a>
 * @param <OD> DTO riêng biệt dùng để hiển thị thông tin cho phía client
 */
@FunctionalInterface
public interface Readable<OD> {

    /**
     * Hiển thị toàn bộ dữ liệu trong cơ sở dữ liệu.
     * @param pageable Đối tượng dùng để <b>phân trang</b> dữ liệu
     * @return Danh sách dữ liệu đã được <b>phân trang</b>, hoặc đã bao gồm cả <b>phân trang + sắp xếp</b>.
     */
    Mono<Page<OD>> findAll(Pageable pageable);
}
