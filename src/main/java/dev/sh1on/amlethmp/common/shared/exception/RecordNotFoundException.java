package dev.sh1on.amlethmp.common.shared.exception;

import java.util.UUID;

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

    public RecordNotFoundException(UUID id) {
        super(String.format("Record with id %s not found", id));
    }
}
