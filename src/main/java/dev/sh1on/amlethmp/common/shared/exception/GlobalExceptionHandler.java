package dev.sh1on.amlethmp.common.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@RestControllerAdvice
@SuppressWarnings("unused")
class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
