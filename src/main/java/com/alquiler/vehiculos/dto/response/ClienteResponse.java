package com.alquiler.vehiculos.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record ClienteResponse(UUID id, String nombre, String documento, LocalDate licenciaVence) {}