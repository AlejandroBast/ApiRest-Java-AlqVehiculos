package com.alquiler.vehiculos.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CrearVehiculoRequest(@NotBlank String placa, @NotBlank String marca, @NotBlank String modelo) {}