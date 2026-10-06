package com.alquiler.vehiculos.dto.response;

import java.util.UUID;

public record VehiculoResponse(UUID id, String placa, String marca, String modelo, String estado) {}