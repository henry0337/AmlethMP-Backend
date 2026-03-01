package dev.sh1on.amlethmp.common.template.service.crud;

import dev.sh1on.amlethmp.common.template.model.SoftDeletableEntity;
import reactor.core.publisher.Mono;

/**
 * <b>[Service-only, Non-standard CRUD, Constrained]</b> <br>
 * Giao diện hàm cung cấp khả năng <b>vô hiệu hóa (disable)</b> và khả năng <b>kích hoạt lại (re-enable)</b> một bản ghi
 * ở phía server.
 * <br><br>
 * <b>Ghi chú</b>:
 * <ul>
 *     <li>Giao diện này <b>không phải</b> là một phần của tiến trình CRUD cơ bản, nên người sử dụng chức năng soft-delete
 *     sẽ cần phải triển khai giao diện này thủ công.</li>
 *     <li>Để implement được giao diện này ở tầng nghiệp vụ, entity đang được tương tác <b>bắt buộc</b> phải kế thừa lớp
 *     {@link SoftDeletableEntity} để kích hoạt chức năng vô hiệu hóa.</li>
 * </ul>
 *
 * @author <a href="https://github.com/henry0337">Amleth</a>
 * @param <K> Điều kiện tìm kiếm dữ liệu cho entity đang tương tác
 */
@FunctionalInterface
public interface Reversible<K> {

    /**
     * Vô hiệu hóa bản ghi dựa trên {@code id} được chỉ định.
     * @param key Điều kiện được chỉ định để tìm kiếm dữ liệu.
     */
    Mono<Void> disableById(K key);
}
