package com.alquiler.vehiculos.exception;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<?> notFound(Exception e) {
        return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class, LicenciaInvalidaException.class})
    ResponseEntity<?> badRequest(Exception e) {
        return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler({IllegalStateException.class, VehiculoNoDisponibleException.class, DevolucionTardiaException.class})
    ResponseEntity<?> conflict(Exception e) {
        return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> datosInvalidos(MethodArgumentNotValidException e) {
        var campo = e.getBindingResult().getFieldErrors().get(0);
        return ResponseEntity.badRequest().body(Map.of("error", campo.getField() + ": " + campo.getDefaultMessage()));
    }
}