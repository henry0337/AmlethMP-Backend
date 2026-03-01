package dev.sh1on.amlethmp.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

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

    public static LocalDateTime ofLocalDateTime(int year, int month, int day, int hour, int minute, int second) {
        return LocalDateTime.of(year, Month.of(month), day, hour, minute, second);
    }
}
