package dev.sh1on.amlethmp.common.template.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Objects;

/**
 * <b>[Domain-only]</b> <br>
 * Lớp trừu tượng giúp đánh dấu một thực thể có khả năng tận dụng cơ chế <b>soft-delete</b> lên các dữ liệu bên trong chúng.
 *
 * @author <a href="https://github.com/henry0337">Amleth</a>
 * @see <a href="https://viblo.asia/p/ban-nen-tranh-su-dung-soft-delete-khi-co-the-va-day-la-ly-do-tai-sao-LzD5dL1E5jY#_i-giai-thich-so-qua-ve-soft-delete-0">Soft-Delete (Xóa mềm)</a>
 */
@Getter
@Setter
public abstract class SoftDeletableEntity extends AmlethMPEntity {

    /**
     * Đánh dấu trạng thái vô hiệu hóa của 1 bản ghi trong cơ sở dữ liệu.
     * <br><br>
     * <b>Giá trị mặc định:</b> {@code false}
     */
    @Column("is_disabled")
    protected boolean isDisabled = false;

    /**
     * Đối tượng mới nhất thực hiện vô hiệu hóa bản ghi này.
     */
    @Column("last_disabled_by")
    protected String lastDisabledBy;

    /**
     * Thời gian lần cuối thực hiện vô hiệu hóa bản ghi này.
     */
    @Column("last_disabled_at")
    protected String lastDisabledAt;

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
