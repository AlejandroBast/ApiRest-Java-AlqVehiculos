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
   
    @Test
    @DisplayName ("verficar que se actualizan los datos del vehiculo")
    void actualizarDatos(){
        Vehiculo vehiculo2 = new Vehiculo("ABD123","totoya","Hilux");

        vehiculo2.actualizar("toyota", "lux");

        assertEquals("toyota", vehiculo2.getMarca());
        assertEquals("lux", vehiculo2.getModelo());
    }

    @Test 
    @DisplayName ("Verificar si se puedee alquilar vehiculo")
    void alquilarVehiculo (){

        Vehiculo vehiculo3 = new Vehiculo("ABC123","totoya","hIlux");
        vehiculo3.alquilar();
        assertEquals(EstadoVehiculo.ALQUILADO,vehiculo3.getEstado());



    }

    @Test 
    @DisplayName ("Verificar que se libero el vehiculo despues de ser alquilado")
    void liberarVehiculo() {
        Vehiculo vehiculo4 = new Vehiculo("ABC123","TOTOYA","HILUX");
        vehiculo4.alquilar();
        vehiculo4.liberar();
        assertEquals(EstadoVehiculo.DISPONIBLE,vehiculo4.getEstado());



    }


}
    