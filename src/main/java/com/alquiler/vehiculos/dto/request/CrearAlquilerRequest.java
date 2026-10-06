package com.alquiler.vehiculos.dto.request;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CrearAlquilerRequest(@NotNull UUID vehiculoId, @NotNull UUID clienteId, @NotNull LocalDate fechaDevolucion) {}