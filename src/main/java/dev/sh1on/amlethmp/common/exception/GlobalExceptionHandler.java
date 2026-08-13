package dev.sh1on.amlethmp.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    ResponseEntity<String> handleUserNotFound(RecordNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
