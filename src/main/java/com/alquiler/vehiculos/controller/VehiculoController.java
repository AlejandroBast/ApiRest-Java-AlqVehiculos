package com.alquiler.vehiculos.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alquiler.vehiculos.dto.request.ActualizarVehiculoRequest;
import com.alquiler.vehiculos.dto.request.CrearVehiculoRequest;
import com.alquiler.vehiculos.dto.response.VehiculoResponse;
import com.alquiler.vehiculos.service.VehiculoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    private final VehiculoService service;

    public VehiculoController(VehiculoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VehiculoResponse> crear(@Valid @RequestBody CrearVehiculoRequest r) {
        return ResponseEntity.status(201).body(service.crear(r));
    }

    @GetMapping
    public List<VehiculoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public VehiculoResponse buscar(@PathVariable UUID id) {
        return service.buscar(id);
    }

    @GetMapping("/placa/{placa}")
    public VehiculoResponse buscarPorPlaca(@PathVariable String placa) {
        return service.buscarPorPlaca(placa);
    }

    @PutMapping("/{id}")
    public VehiculoResponse actualizar(@PathVariable UUID id, @Valid @RequestBody ActualizarVehiculoRequest r) {
        return service.actualizar(id, r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}