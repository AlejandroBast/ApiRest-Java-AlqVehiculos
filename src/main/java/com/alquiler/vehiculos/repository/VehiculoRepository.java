package com.alquiler.vehiculos.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alquiler.vehiculos.domain.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, UUID> {
    Optional<Vehiculo> findByPlaca(String placa);

    boolean existsByPlaca(String placa);
}