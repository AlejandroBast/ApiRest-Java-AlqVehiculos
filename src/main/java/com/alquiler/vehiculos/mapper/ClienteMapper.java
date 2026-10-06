package com.alquiler.vehiculos.mapper;

import com.alquiler.vehiculos.domain.Cliente;
import com.alquiler.vehiculos.dto.response.ClienteResponse;

public class ClienteMapper {

    public static ClienteResponse toResponse(Cliente c) {
        return new ClienteResponse(c.getId(), c.getNombre(), c.getDocumento(), c.getLicenciaVence());
    }
}