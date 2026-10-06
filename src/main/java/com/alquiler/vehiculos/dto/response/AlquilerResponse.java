package com.alquiler.vehiculos.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record AlquilerResponse(UUID id, String placa, String cliente, LocalDate fechaInicio,
                               LocalDate fechaDevolucionPactada, LocalDate fechaDevolucionReal, String estado) {}