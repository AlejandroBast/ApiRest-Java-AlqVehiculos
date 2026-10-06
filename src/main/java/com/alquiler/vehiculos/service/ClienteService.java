package com.alquiler.vehiculos.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alquiler.vehiculos.domain.Cliente;
import com.alquiler.vehiculos.dto.request.ActualizarClienteRequest;
import com.alquiler.vehiculos.dto.request.CrearClienteRequest;
import com.alquiler.vehiculos.dto.response.ClienteResponse;
import com.alquiler.vehiculos.exception.NotFoundException;
import com.alquiler.vehiculos.mapper.ClienteMapper;
import com.alquiler.vehiculos.repository.AlquilerRepository;
import com.alquiler.vehiculos.repository.ClienteRepository;

@Service
public class ClienteService {
    private final ClienteRepository repo;
    private final AlquilerRepository alquilerRepo;

    public ClienteService(ClienteRepository repo, AlquilerRepository alquilerRepo) {
        this.repo = repo;
        this.alquilerRepo = alquilerRepo;
    }

    @Transactional
    public ClienteResponse crear(CrearClienteRequest r) {
        if (repo.existsByDocumento(r.documento().trim())) {
            throw new IllegalStateException("Ya existe un cliente con el documento " + r.documento());
        }
        return ClienteMapper.toResponse(repo.save(new Cliente(r.nombre(), r.documento(), r.licenciaVence())));
    }

    @Transactional(readOnly = true)
    public List<ClienteResponse> listar() {
        return repo.findAll().stream().map(ClienteMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse buscar(UUID id) {
        return ClienteMapper.toResponse(buscarEntidad(id));
    }

    @Transactional
    public ClienteResponse actualizar(UUID id, ActualizarClienteRequest r) {
        Cliente c = buscarEntidad(id);
        c.actualizar(r.nombre(), r.licenciaVence());
        return ClienteMapper.toResponse(c);
    }

    @Transactional
    public void eliminar(UUID id) {
        Cliente c = buscarEntidad(id);
        if (alquilerRepo.existsByClienteId(id)) {
            throw new IllegalStateException("No se puede eliminar un cliente con alquileres registrados");
        }
        repo.delete(c);
    }

    private Cliente buscarEntidad(UUID id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
    }
}