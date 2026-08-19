package dev.sh1on.amlethmp.common.enums;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <b>[Constant]</b> <br>
 * Lớp định nghĩa <b>thứ tự ưu tiên khởi tạo</b> các
 * {@link org.springframework.context.annotation.Bean bean}/{@link org.springframework.stereotype.Component component} 
 * của framework.
 * 
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InitializationPriority {
    private static final int FIRST = 1;
    private static final int SECOND = 2;

    public static final int THIRD = 3;
}
