package com.alquiler.vehiculos.domain;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;


class ClienteTest {
    @Test 
    @DisplayName ("crear cliente, guardar nombre, documento y licecncia")
    void crearCliente (){
        Cliente cliente1= new Cliente ("Juan","123456",  LocalDate.now().plusDays(1));
        assertEquals("Juan", cliente1.getNombre());
        assertEquals("123456", cliente1.getDocumento());
        assertEquals(LocalDate.now().plusDays(1), cliente1.getLicenciaVence());
    }


}


   
    
