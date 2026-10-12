package com.alquiler.vehiculos.domain;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;




class AlquilerTest {
    @Test
    @DisplayName("Crear alquiler, lo deja activo y alquila el vehiculo")
    void CrearAlquiler() {
        
        Vehiculo vehiculo1 = new Vehiculo("ABC123", "Toyota", "Camry");
        Cliente cliente1 = new Cliente("nombre","123456", LocalDate.now().plusDays(1));
        Alquiler alquiler1 = new Alquiler(vehiculo1, cliente1, LocalDate.now().plusDays(5));
        assertEquals(EstadoAlquiler.ACTIVO, alquiler1.getEstado());
        assertEquals(EstadoVehiculo.ALQUILADO, vehiculo1.getEstado());
    }

}
