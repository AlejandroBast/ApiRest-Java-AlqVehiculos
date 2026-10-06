package com.alquiler.vehiculos.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alquiler.vehiculos.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    boolean existsByDocumento(String documento);
}