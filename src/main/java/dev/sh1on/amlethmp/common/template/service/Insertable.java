package dev.sh1on.amlethmp.common.template.service;

import reactor.core.publisher.Mono;

/**
 * <b>[Service-only, Standard CRUD]</b> <br>
 * Giao diện hàm cung cấp khả năng <b>thêm mới</b> dữ liệu thuộc tác vụ CRUD tiêu chuẩn.
 *
 * @author <a href="https://github.com/henry0337">Amleth</a>
 * @param <OD> DTO riêng biệt dùng để hiển thị thông tin cho phía client
 * @param <CD> DTO riêng biệt dùng để thêm mới dữ liệu
 */
@FunctionalInterface
public interface Insertable<OD, CD> {

    /**
     * Thêm dữ liệu mới vào một bản ghi và lưu vào cơ sở dữ liệu.
     * @param dto Dữ liệu của bản ghi mới
     * @return Bản ghi vừa mới được thêm
     */
    Mono<OD> save(CD dto);
}
