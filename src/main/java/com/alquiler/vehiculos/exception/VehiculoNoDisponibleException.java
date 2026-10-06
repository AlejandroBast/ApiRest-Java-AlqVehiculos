package com.alquiler.vehiculos.exception;

public class VehiculoNoDisponibleException extends RuntimeException {
    public VehiculoNoDisponibleException(String placa) {
        super("El vehiculo " + placa + " ya esta alquilado");
    }
}