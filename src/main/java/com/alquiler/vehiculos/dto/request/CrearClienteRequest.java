package com.alquiler.vehiculos.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record CrearClienteRequest(@NotBlank String nombre, @NotBlank String documento, LocalDate licenciaVence) {}