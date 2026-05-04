package com.carbonoil.gestione_carburante.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SerbatoioCriticoException.class)
    public ResponseEntity<ErrorResponse> handleSerbatoioCritico(SerbatoioCriticoException ex) {
        ErrorResponse error = new ErrorResponse(
            ex.getMessage(), 
            HttpStatus.BAD_REQUEST.value(), 
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(
            ex.getMessage(), 
            HttpStatus.BAD_REQUEST.value(), 
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            "Errore interno", 
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}