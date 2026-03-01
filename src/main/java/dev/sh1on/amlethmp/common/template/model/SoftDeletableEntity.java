package dev.sh1on.amlethmp.common.template.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Column;

/**
 * <b>[Domain-only]</b> <br>
 * Lớp trừu tượng giúp đánh dấu một thực thể có khả năng tận dụng cơ chế <b>soft-delete</b> lên các dữ liệu bên trong chúng.
 * @author <a href="https://github.com/henry0337">Amleth</a>
 * @see <a href="https://viblo.asia/p/ban-nen-tranh-su-dung-soft-delete-khi-co-the-va-day-la-ly-do-tai-sao-LzD5dL1E5jY#_i-giai-thich-so-qua-ve-soft-delete-0">Soft-Delete (Xóa mềm)</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
}
