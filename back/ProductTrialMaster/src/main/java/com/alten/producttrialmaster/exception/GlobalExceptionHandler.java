package com.alten.producttrialmaster.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle create product exception
    @ExceptionHandler(CreateProductException.class)
    public ResponseEntity<?> handleCreateProductException(CreateProductException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    // Handle get product exception
    @ExceptionHandler(GetProductException.class)
    public ResponseEntity<?> handleGetProductException(GetProductException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    // Handle update product exception
    @ExceptionHandler(UpdateProductException.class)
    public ResponseEntity<?> handleUpdateProductException(UpdateProductException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    // Handle delete product exception
    @ExceptionHandler(DeleteProductException.class)
    public ResponseEntity<?> handleDeleteProductException(DeleteProductException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // Handle unsupported storage type exception
    @ExceptionHandler(UnsupportedStorageTypeException.class)
    public ResponseEntity<?> handleUnsupportedStorageTypeException(UnsupportedStorageTypeException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // Handle global exceptions (e.g., general RuntimeExceptions)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("An error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
