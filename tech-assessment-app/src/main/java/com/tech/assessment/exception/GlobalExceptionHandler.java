package com.tech.assessment.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeExceptions(RuntimeException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidFormatException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Map<String, Object>> handleJsonParseErrors(Exception ex) {
        Throwable cause = ex instanceof HttpMessageNotReadableException
                ? ex.getCause()
                : ex;

        if (cause instanceof InvalidFormatException ife && ife.getTargetType().isEnum()) {
            Class<?> enumType = ife.getTargetType();
            Object[] validValues = enumType.getEnumConstants();

            Map<String, Object> response = new HashMap<>();
            response.put("error", "Invalid enum value");
            response.put("field", ife.getPath().isEmpty() ? "unknown" : ife.getPath().get(0).getFieldName());
            response.put("invalidValue", ife.getValue());
            response.put("allowedValues", validValues);

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        // Generic fallback for any other parse issues
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Invalid JSON format");
        response.put("details", ex.getLocalizedMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String extractFieldName(String pathReference) {
        if (pathReference == null) return "unknown";
        int dotIndex = pathReference.lastIndexOf('.');
        return dotIndex != -1 ? pathReference.substring(dotIndex + 1) : pathReference;
    }
}
