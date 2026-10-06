package com.alquiler.vehiculos.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ActualizarVehiculoRequest(@NotBlank String marca, @NotBlank String modelo) {}