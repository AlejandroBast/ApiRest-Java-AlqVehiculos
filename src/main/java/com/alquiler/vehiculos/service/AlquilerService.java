package com.alquiler.vehiculos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alquiler.vehiculos.domain.Alquiler;
import com.alquiler.vehiculos.domain.Cliente;
import com.alquiler.vehiculos.domain.Vehiculo;
import com.alquiler.vehiculos.dto.request.CrearAlquilerRequest;
import com.alquiler.vehiculos.dto.response.AlquilerResponse;
import com.alquiler.vehiculos.exception.DevolucionTardiaException;
import com.alquiler.vehiculos.exception.NotFoundException;
import com.alquiler.vehiculos.exception.VehiculoNoEncontradoException;
import com.alquiler.vehiculos.mapper.AlquilerMapper;
import com.alquiler.vehiculos.repository.AlquilerRepository;
import com.alquiler.vehiculos.repository.ClienteRepository;
import com.alquiler.vehiculos.repository.VehiculoRepository;

@Service
public class AlquilerService {
    private final AlquilerRepository repo;
    private final VehiculoRepository vehiculoRepo;
    private final ClienteRepository clienteRepo;

    public AlquilerService(AlquilerRepository repo, VehiculoRepository vehiculoRepo, ClienteRepository clienteRepo) {
        this.repo = repo;
        this.vehiculoRepo = vehiculoRepo;
        this.clienteRepo = clienteRepo;
    }

    @Transactional
    public AlquilerResponse crear(CrearAlquilerRequest r) {
        Vehiculo vehiculo = vehiculoRepo.findById(r.vehiculoId())
            .orElseThrow(() -> new VehiculoNoEncontradoException(r.vehiculoId().toString()));
        Cliente cliente = clienteRepo.findById(r.clienteId())
            .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
        return AlquilerMapper.toResponse(repo.save(new Alquiler(vehiculo, cliente, r.fechaDevolucion())));
    }

    // la devolucion tardia se guarda igual y despues se avisa con la excepcion
    @Transactional(noRollbackFor = DevolucionTardiaException.class)
    public AlquilerResponse devolver(UUID id) {
        Alquiler a = buscarEntidad(id);
        a.devolver(LocalDate.now());
        return AlquilerMapper.toResponse(a);
    }

    @Transactional(readOnly = true)
    public List<AlquilerResponse> listar() {
        return repo.findAll().stream().map(AlquilerMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AlquilerResponse buscar(UUID id) {
        return AlquilerMapper.toResponse(buscarEntidad(id));
    }

    private Alquiler buscarEntidad(UUID id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Alquiler no encontrado"));
    }
}