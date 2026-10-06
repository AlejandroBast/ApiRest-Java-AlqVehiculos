package com.alquiler.vehiculos.exception;

public class DevolucionTardiaException extends RuntimeException {
    public DevolucionTardiaException(long diasTarde) {
        super("Devolucion tardia: " + diasTarde + " dia(s) despues de la fecha pactada. La devolucion quedo registrada");
    }
}