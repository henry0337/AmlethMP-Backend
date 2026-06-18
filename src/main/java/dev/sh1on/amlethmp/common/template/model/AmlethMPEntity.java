package dev.sh1on.amlethmp.common.template.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;

import java.time.OffsetDateTime;

/**
 * <b>[Internal, Model-only]</b> <br>
 * Lớp trừu tượng cơ sở đại diện cho một thực thể (Entity) trong hệ thống <b>AmlethMP</b>.
 * <p>
 * Lớp này cung cấp các thuộc tính metadata chung như ID, phiên bản (Optimistic Locking),
 * và thông tin kiểm toán (Auditing) bao gồm thời gian tạo, người tạo, thời gian cập nhật
 * và người cập nhật cuối cùng.
 * </p>
 *
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Getter
@Setter
@SuppressWarnings("java:S1694")
public abstract class AmlethMPEntity {
    /**
     * Khóa chính của thực thể.
     */
    @Id
    protected String id;

    /**
     * Phiên bản của thực thể, dùng để hỗ trợ cơ chế <b>Optimistic Locking</b>.
     */
    @Version
    protected Integer version;

    /**
     * Thời điểm bản ghi được tạo lập.
     */
    @Column("created_at")
    @CreatedDate
    @InsertOnlyProperty
    protected OffsetDateTime createdAt;

    /**
     * Đối tượng thực hiện tạo lập bản ghi này.
     */
    @Column("created_by")
    @CreatedBy
    @InsertOnlyProperty
    protected String createdBy;

    /**
     * Thời điểm bản ghi được cập nhật lần cuối.
     */
    @Column("last_updated_at")
    @LastModifiedDate
    protected OffsetDateTime lastUpdatedAt;

    /**
     * Đối tượng thực hiện cập nhật bản ghi này lần cuối.
     */
    @Column("last_updated_by")
    @LastModifiedBy
    protected String lastUpdatedBy;
}
