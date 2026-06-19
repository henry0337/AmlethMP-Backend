package dev.sh1on.amlethmp.common.template.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * <b>[Internal, Model-only]</b> <br>
 * Lớp trừu tượng giúp đánh dấu một thực thể có khả năng tận dụng cơ chế <b>soft-delete</b> (xóa mềm)
 * để quản lý vòng đời của dữ liệu mà không thực sự xóa chúng khỏi hệ thống.
 * <p>
 * Khi một thực thể được "xóa", trạng thái {@code isDisabled} sẽ được chuyển sang {@code true},
 * kèm theo thông tin về người thực hiện và thời điểm thực hiện.
 * </p>
 *
 * @author <a href="https://github.com/henry0337">S3lena</a>
 * @see AmlethMPEntity
 * @see <a href="https://viblo.asia/p/ban-nen-tranh-su-dung-soft-delete-khi-co-the-va-day-la-ly-do-tai-sao-LzD5dL1E5jY#_i-giai-thich-so-qua-ve-soft-delete-0">Tìm hiểu về Soft-Delete</a>
 */
@Getter
@Setter
public abstract class SoftDeletableEntity extends AmlethMPEntity {

    /**
     * Trạng thái vô hiệu hóa của bản ghi.
     * <p>
     * Nếu giá trị là {@code true}, bản ghi được coi là đã bị xóa mềm và thường sẽ bị bỏ qua
     * trong các câu truy vấn thông thường.
     * </p>
     * <b>Giá trị mặc định:</b> {@code false}
     */
    @Column("is_disabled")
    protected boolean isDisabled;

    /**
     * Định danh của đối tượng thực hiện vô hiệu hóa bản ghi này.
     */
    @Column("last_disabled_by")
    protected String lastDisabledBy;

    /**
     * Thời điểm thực hiện vô hiệu hóa bản ghi này.
     */
    @Column("last_disabled_at")
    protected OffsetDateTime lastDisabledAt;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SoftDeletableEntity that = (SoftDeletableEntity) o;
        return isDisabled == that.isDisabled && Objects.equals(lastDisabledBy, that.lastDisabledBy) && Objects.equals(lastDisabledAt, that.lastDisabledAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isDisabled, lastDisabledBy, lastDisabledAt);
    }
}
