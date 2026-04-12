package dev.sh1on.amlethmp.common.shared.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * Lớp tiện ích chung cung cấp các phương thức hỗ trợ xử lý dữ liệu cơ bản.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtils {

    /**
     * Đảm bảo đối tượng không thể {@code null}.
     * <p>{@linkplain NullPointerException NPE} sẽ được ném ra nếu như đối tượng được truyền vào có giá trị {@code null}.</p>
     *
     * @param instance đối tượng cần kiểm tra
     * @param <T>      kiểu dữ liệu của đối tượng
     * @return đối tượng nếu nó không {@code null}
     * @throws NullPointerException nếu đối tượng là {@code null}
     */
    @Contract(value = "null -> fail; _ -> param1", pure = true)
    public static <T> T asNonNullable(@Nullable T instance) {
        return Objects.requireNonNull(instance);
    }

    /**
     * Trả về đối tượng nếu nó không {@code null}, ngược lại trả về giá trị mặc định được cung cấp.
     *
     * @param instance     đối tượng cần kiểm tra
     * @param defaultValue giá trị mặc định nếu đối tượng là {@code null}
     * @param <T>          kiểu dữ liệu của đối tượng
     * @return đối tượng ban đầu hoặc giá trị mặc định
     */
    @Contract(value = "!null, _ -> param1", pure = true)
    public static <T> T asNonNullable(@Nullable T instance, T defaultValue) {
        return Objects.requireNonNullElse(instance, defaultValue); // NOSONAR
    }
}
