package dev.sh1on.amlethmp.common.shared.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.Nullable;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeUtils {

    /**
     * Tạo {@link Duration} từ số ngày, giờ, phút, giây.
     *
     * @param days    số ngày
     * @param hours   số giờ
     * @param minutes số phút
     * @param seconds số giây
     * @return Duration tương ứng
     */
    public static Duration ofDuration(long days, long hours, long minutes, long seconds) {
        return Duration.ofDays(days)
                .plusHours(hours)
                .plusMinutes(minutes)
                .plusSeconds(seconds);
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static LocalDateTime ofLocalDateTime(int year, int month, int day, int hour, int minute, int second) {
        return LocalDateTime.of(year, Month.of(month), day, hour, minute, second);
    }

    /**
     * Chuyển đổi {@link OffsetDateTime} sang {@link LocalDateTime} dựa trên múi giờ mặc định của hệ thống.
     * Việc chuyển đổi này đảm bảo thời gian được "localized" (địa phương hóa) về múi giờ hiện tại của máy chủ.
     *
     * @param offsetDateTime Giá trị thời gian cần chuyển đổi
     * @return {@link LocalDateTime} tương ứng ở múi giờ hệ thống, hoặc {@code null} nếu đầu vào là null.
     * @author <a href="https://github.com/henry0337">S3lena</a>
     */
    @Contract("null -> null")
    public static @Nullable LocalDateTime toLocalDateTime(@Nullable OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) return null;
        return offsetDateTime.atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Chuyển đổi {@link OffsetDateTime} sang {@link Date} (kiểu dữ liệu thời gian cũ thường dùng ở các thư viện cũ).
     * {@link Date} lưu trữ khoảnh khắc (instant) nên không bị phụ thuộc vào múi giờ.
     *
     * @param offsetDateTime Giá trị thời gian cần chuyển đổi
     * @return Đối tượng {@link Date} đại diện cho cùng một thời điểm, hoặc {@code null} nếu đầu vào là null.
     * @author <a href="https://github.com/henry0337">S3lena</a>
     */
    @Contract("null -> null; !null -> !null")
    public static @Nullable Date toDate(@Nullable OffsetDateTime offsetDateTime) {
        if (offsetDateTime == null) return null;
        return Date.from(offsetDateTime.toInstant());
    }
}
