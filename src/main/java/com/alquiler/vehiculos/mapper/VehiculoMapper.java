package com.alquiler.vehiculos.mapper;

import com.alquiler.vehiculos.domain.Vehiculo;
import com.alquiler.vehiculos.dto.response.VehiculoResponse;

public class VehiculoMapper {

    public static VehiculoResponse toResponse(Vehiculo v) {
        return new VehiculoResponse(v.getId(), v.getPlaca(), v.getMarca(), v.getModelo(), v.getEstado().name());
    }
}