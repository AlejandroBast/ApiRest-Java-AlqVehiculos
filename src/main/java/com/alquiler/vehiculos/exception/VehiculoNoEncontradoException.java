package com.alquiler.vehiculos.exception;

public class VehiculoNoEncontradoException extends NotFoundException {
    public VehiculoNoEncontradoException(String busqueda) {
        super("Vehiculo no encontrado: " + busqueda);
    }
}