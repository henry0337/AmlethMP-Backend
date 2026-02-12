package dev.sh1on.amlethmp.common.template.service;

import dev.sh1on.amlethmp.common.template.service.crud.Deletable;
import dev.sh1on.amlethmp.common.template.service.crud.Insertable;
import dev.sh1on.amlethmp.common.template.service.crud.Modifiable;
import dev.sh1on.amlethmp.common.template.service.crud.ReadableWithKey;
import org.springframework.stereotype.Service;

/**
 * <b>[Internal, Service-only]</b> <br>
 * Giao diện phía {@linkplain Service nghiệp vụ} cung cấp các phương thức CRUD tối thiểu cần thiết cho một Entity.
 * @author <a href="https://github.com/henry0337">Amleth</a>
 * @param <OD> DTO riêng biệt dùng để hiển thị thông tin cho phía client
 * @param <K> Điều kiện tìm kiếm entity đang tương tác
 * @param <CD> DTO riêng biệt dùng để thêm mới dữ liệu
 * @param <UD> DTO riêng biệt dùng để cập nhật dữ liệu
 */
public interface AmlethMPRestService<OD, K, CD, UD> extends
        ReadableWithKey<OD, K>,
        Insertable<OD, CD>,
        Modifiable<OD, K, UD>,
        Deletable<K> { }