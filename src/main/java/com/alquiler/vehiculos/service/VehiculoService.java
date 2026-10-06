package com.alquiler.vehiculos.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alquiler.vehiculos.domain.Vehiculo;
import com.alquiler.vehiculos.dto.request.ActualizarVehiculoRequest;
import com.alquiler.vehiculos.dto.request.CrearVehiculoRequest;
import com.alquiler.vehiculos.dto.response.VehiculoResponse;
import com.alquiler.vehiculos.exception.VehiculoNoEncontradoException;
import com.alquiler.vehiculos.mapper.VehiculoMapper;
import com.alquiler.vehiculos.repository.AlquilerRepository;
import com.alquiler.vehiculos.repository.VehiculoRepository;

@Service
public class VehiculoService {
    private final VehiculoRepository repo;
    private final AlquilerRepository alquilerRepo;

    public VehiculoService(VehiculoRepository repo, AlquilerRepository alquilerRepo) {
        this.repo = repo;
        this.alquilerRepo = alquilerRepo;
    }

    @Transactional
    public VehiculoResponse crear(CrearVehiculoRequest r) {
        if (repo.existsByPlaca(r.placa().trim().toUpperCase())) {
            throw new IllegalStateException("Ya existe un vehiculo con la placa " + r.placa());
        }
        return VehiculoMapper.toResponse(repo.save(new Vehiculo(r.placa(), r.marca(), r.modelo())));
    }

    @Transactional(readOnly = true)
    public List<VehiculoResponse> listar() {
        return repo.findAll().stream().map(VehiculoMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public VehiculoResponse buscar(UUID id) {
        return VehiculoMapper.toResponse(buscarEntidad(id));
    }

    @Transactional(readOnly = true)
    public VehiculoResponse buscarPorPlaca(String placa) {
        Vehiculo v = repo.findByPlaca(placa.trim().toUpperCase())
            .orElseThrow(() -> new VehiculoNoEncontradoException(placa));
        return VehiculoMapper.toResponse(v);
    }

    @Transactional
    public VehiculoResponse actualizar(UUID id, ActualizarVehiculoRequest r) {
        Vehiculo v = buscarEntidad(id);
        v.actualizar(r.marca(), r.modelo());
        return VehiculoMapper.toResponse(v);
    }

    @Transactional
    public void eliminar(UUID id) {
        Vehiculo v = buscarEntidad(id);
        if (alquilerRepo.existsByVehiculoId(id)) {
            throw new IllegalStateException("No se puede eliminar un vehiculo con alquileres registrados");
        }
        repo.delete(v);
    }

    private Vehiculo buscarEntidad(UUID id) {
        return repo.findById(id).orElseThrow(() -> new VehiculoNoEncontradoException(id.toString()));
    }
}