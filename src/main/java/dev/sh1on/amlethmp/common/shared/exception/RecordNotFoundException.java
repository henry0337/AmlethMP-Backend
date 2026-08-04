package dev.sh1on.amlethmp.common.shared.exception;

/**
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
public class RecordNotFoundException extends RuntimeException {
    /**
     * Khởi tạo một instance của exception này mà không có message mô tả lỗi nào.
     */
    public RecordNotFoundException() {
        super();
    }

    /**
     * Khởi tạo một instance của exception này cùng với {@code message} chi tiết.
     *
     * @param message Message chi tiết
     */
    public RecordNotFoundException(String message) {
        super(message);
    }
}
