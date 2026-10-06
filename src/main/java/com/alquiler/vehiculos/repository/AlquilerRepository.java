package com.alquiler.vehiculos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alquiler.vehiculos.domain.Alquiler;

public interface AlquilerRepository extends JpaRepository<Alquiler, UUID> {
    boolean existsByVehiculoId(UUID vehiculoId);

    boolean existsByClienteId(UUID clienteId);
}