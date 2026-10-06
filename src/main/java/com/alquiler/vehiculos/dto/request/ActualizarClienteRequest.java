package com.alquiler.vehiculos.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record ActualizarClienteRequest(@NotBlank String nombre, LocalDate licenciaVence) {}