package dev.sh1on.amlethmp.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
/**
 * <b>[Constant Class]</b> <br>
 * Lớp định nghĩa <b>thứ tự ưu tiên khởi tạo</b> các
 * {@link org.springframework.context.annotation.Bean bean}/{@link org.springframework.stereotype.Component component} 
 * của framework.
 * <p>Mỗi constant trong này sẽ đại điện cho từng thành phần trên theo thứ tự dựa trên tên của constant, kèm với tên các
 * thành phần tương ứng trong doc comment.</p>
 * 
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InitializationPriority {
    /**
     * Circuit Breaker (Resilience4J).
     * 
     * <p><b>Ghi chú:</b> Được đánh dấu là {@code private} do được quản lý thông qua thư viện cung cấp nó, cũng như
     * qua {@code application.yaml} của project.</p>
     */
    private static final int FIRST = 1;

    /**
     * Retry (Resilience4J).
     * 
     * <p><b>Ghi chú:</b> Được đánh dấu là {@code private} do được quản lý thông qua thư viện cung cấp nó, cũng như
     * qua {@code application.yaml} của project.</p>
     */
    private static final int SECOND = 2;

    /**
     * Trình khởi động Swagger UI.
     * @see dev.sh1on.amlethmp.common.event.SwaggerUiInitializer SwaggerUiInitializer
     */
    public static final int THIRD = 3;
}
