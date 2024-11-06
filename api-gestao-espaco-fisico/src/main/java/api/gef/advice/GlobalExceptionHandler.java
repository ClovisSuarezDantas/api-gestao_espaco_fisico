package api.gef.advice;

import api.gef.exception.GlobalException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> handleExceptions(GlobalException runtimeException) {
        return ResponseEntity.status(runtimeException.getErrorVO().code()).body(runtimeException.getErrorVO());
    }
}
