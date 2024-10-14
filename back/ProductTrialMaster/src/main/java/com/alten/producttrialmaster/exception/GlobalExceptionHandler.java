package com.alten.producttrialmaster.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Handle create product exception
    @ExceptionHandler(CreateProductException.class)
    public ResponseEntity<?> handleCreateProductException(CreateProductException ex) {
        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    // Handle get product exception
    @ExceptionHandler(GetProductException.class)
    public ResponseEntity<?> handleGetProductException(GetProductException ex) {
        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    // Handle update product exception
    @ExceptionHandler(UpdateProductException.class)
    public ResponseEntity<?> handleUpdateProductException(UpdateProductException ex) {
        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    // Handle delete product exception
    @ExceptionHandler(DeleteProductException.class)
    public ResponseEntity<?> handleDeleteProductException(DeleteProductException ex) {
        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // Handle unsupported storage type exception
    @ExceptionHandler(UnsupportedStorageTypeException.class)
    public ResponseEntity<?> handleUnsupportedStorageTypeException(UnsupportedStorageTypeException ex) {
        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(),ex);
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    errors.append(fieldError.getField());
                    errors.append(fieldError.getDefaultMessage());
                });
        return new ResponseEntity<>("Validation error : "+errors, ex.getStatusCode());
    }

    // Handle global exceptions (e.g., general RuntimeExceptions)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>("An error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
