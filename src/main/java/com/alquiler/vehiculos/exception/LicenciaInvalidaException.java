package com.alquiler.vehiculos.exception;

public class LicenciaInvalidaException extends RuntimeException {
    public LicenciaInvalidaException(String cliente) {
        super("El cliente " + cliente + " no tiene una licencia de conduccion valida");
    }
}