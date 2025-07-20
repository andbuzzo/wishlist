package com.caseluizalabs.wishlist.config.exception;

import com.caseluizalabs.wishlist.core.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<Object> handleWishListNotFound(WishlistNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WishlistEmptyException.class)
    public ResponseEntity<Object> handleWishListEmpty(WishlistEmptyException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotInWishlistException.class)
    public ResponseEntity<Object> handleProductNotExists(ProductNotInWishlistException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProductAlreadyInWishlistException.class)
    public ResponseEntity<Object> handleProductExists(ProductAlreadyInWishlistException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WishlistLimitExceededException.class)
    public ResponseEntity<Object> handleLimitExceeded(WishlistLimitExceededException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        return buildResponse("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ValidationErrorResponse errors = new ValidationErrorResponse();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, status);
    }

    public static class ValidationErrorResponse {
        private final List<FieldValidationError> errors = new ArrayList<>();

        public void addError(String field, String message) {
            errors.add(new FieldValidationError(field, message));
        }

        public List<FieldValidationError> getErrors() {
            return errors;
        }
    }

    public static class FieldValidationError {
        private final String field;
        private final String message;

        public FieldValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }

}
