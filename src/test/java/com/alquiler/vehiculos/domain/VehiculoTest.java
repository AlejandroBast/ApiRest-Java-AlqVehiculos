package com.alquiler.vehiculos.domain;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;


class VehiculoTest {
    @Test
    @DisplayName("Crear vehiculo, guardar placa, marca y modelo")
    void crearVehiculo (){
        Vehiculo vehiculo1 = new Vehiculo("ABC123","totoya","Hilux");
        assertEquals("ABC123",vehiculo1.getPlaca());
        assertEquals("totoya",vehiculo1.getMarca());
        assertEquals("Hilux",vehiculo1.getModelo());
    }
   
}
    