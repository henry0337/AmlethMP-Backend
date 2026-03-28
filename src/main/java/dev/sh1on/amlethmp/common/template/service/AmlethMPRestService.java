package dev.sh1on.amlethmp.common.template.service;

import dev.sh1on.amlethmp.common.template.service.crud.Deletable;
import dev.sh1on.amlethmp.common.template.service.crud.Insertable;
import dev.sh1on.amlethmp.common.template.service.crud.Modifiable;
import dev.sh1on.amlethmp.common.template.service.crud.ReadableWithKey;

/**
 * <b>[Internal, Service-only]</b> <br>
 * Giao diện phía {@linkplain org.springframework.stereotype.Service nghiệp vụ} cung cấp các phương thức CRUD tối thiểu cần thiết cho một Entity.
 * <p>
 * Lớp này kế thừa từ {@link AmlethMPService} và triển khai các giao diện chức năng như
 * {@link ReadableWithKey}, {@link Insertable}, {@link Modifiable}, và {@link Deletable}
 * để đảm bảo tính thống nhất trong việc triển khai logic nghiệp vụ.
 * </p>
 *
 * @param <OD> Output DTO (Dùng để hiển thị thông tin cho phía client)
 * @param <K>  Key (Điều kiện tìm kiếm entity, thường là ID)
 * @param <CD> Create DTO (Dùng để thêm mới dữ liệu)
 * @param <UD> Update DTO (Dùng để cập nhật dữ liệu)
 * @author <a href="https://github.com/henry0337">S3lena</a>
 * @see AmlethMPService
 * @see ReadableWithKey
 * @see Insertable
 * @see Modifiable
 * @see Deletable
 */
public abstract class AmlethMPRestService<OD, K, CD, UD> extends AmlethMPService implements
        ReadableWithKey<OD, K>,
        Insertable<OD, CD>,
        Modifiable<OD, K, UD>,
        Deletable<K> { }