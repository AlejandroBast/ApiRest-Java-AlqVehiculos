package com.alquiler.vehiculos.mapper;

import com.alquiler.vehiculos.domain.Alquiler;
import com.alquiler.vehiculos.dto.response.AlquilerResponse;

public class AlquilerMapper {

    public static AlquilerResponse toResponse(Alquiler a) {
        return new AlquilerResponse(a.getId(), a.getVehiculo().getPlaca(), a.getCliente().getNombre(),
            a.getFechaInicio(), a.getFechaDevolucionPactada(), a.getFechaDevolucionReal(), a.getEstado().name());
    }
}