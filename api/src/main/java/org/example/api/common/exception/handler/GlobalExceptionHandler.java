package org.example.api.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.api.common.exception.model.ErrorResponse;
import org.example.core.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleServerError(BindException e) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        ErrorResponse<Map<String, String>> errorResponse = ErrorResponse.error(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                "잘못된 요청입니다.",
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse<Map<String, String>>> handleBusinessException(BusinessException e) {
        ErrorResponse<Map<String, String>> errorResponse = ErrorResponse.error(
                e.getStatusCode(),
                e.getMessage(),
                e.getValidation()
        );

        return ResponseEntity.status(HttpStatus.valueOf(Integer.parseInt(e.getStatusCode())))
                .body(errorResponse);
    }
}
