package com.alquiler.vehiculos.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alquiler.vehiculos.dto.request.CrearAlquilerRequest;
import com.alquiler.vehiculos.dto.response.AlquilerResponse;
import com.alquiler.vehiculos.service.AlquilerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {
    private final AlquilerService service;

    public AlquilerController(AlquilerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AlquilerResponse> crear(@Valid @RequestBody CrearAlquilerRequest r) {
        return ResponseEntity.status(201).body(service.crear(r));
    }

    @GetMapping
    public List<AlquilerResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public AlquilerResponse buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @PostMapping("/{id}/devolver")
    public AlquilerResponse devolver(@PathVariable UUID id) {
        return service.devolver(id);
    }
}