package dev.sh1on.amlethmp.common.template.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Đánh dấu một thực thể có thể sử dụng cơ chế <b>soft-delete</b> lên các dữ liệu bên trong chúng.
 * @author <a href="https://github.com/henry0337">Amleth</a>
 * @see <a href="https://viblo.asia/p/ban-nen-tranh-su-dung-soft-delete-khi-co-the-va-day-la-ly-do-tai-sao-LzD5dL1E5jY#_i-giai-thich-so-qua-ve-soft-delete-0">Soft-Delete (Xóa mềm)</a>
 */
@Getter
@Setter
public abstract class SoftDeletableEntity {

    /**
     * Đánh dấu trạng thái vô hiệu hóa của 1 bản ghi trong cơ sở dữ liệu.
     * <br><br>
     * <b>Giá trị mặc định:</b> {@code false}
     */
    protected boolean isDisabled = false;

    /**
     * Đối tượng mới nhất thực hiện vô hiệu hóa bản ghi này.
     */
    protected String disabledBy;

    /**
     * Thời gian thực hiện vô hiệu hóa bản ghi này.
     */
    protected LocalDateTime disabledAt;
}
