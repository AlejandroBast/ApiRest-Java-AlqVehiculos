package com.alquiler.vehiculos.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.alquiler.vehiculos.exception.DevolucionTardiaException;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alquileres")
public class Alquiler {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private LocalDate fechaInicio;
    private LocalDate fechaDevolucionPactada;
    private LocalDate fechaDevolucionReal;

    @Enumerated(EnumType.STRING)
    private EstadoAlquiler estado;

    protected Alquiler() {}

    public Alquiler(Vehiculo vehiculo, Cliente cliente, LocalDate fechaDevolucionPactada) {
        if (fechaDevolucionPactada == null || fechaDevolucionPactada.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de devolucion no puede estar en el pasado");
        }
        cliente.validarLicencia();
        vehiculo.alquilar();
        this.vehiculo = vehiculo;
        this.cliente = cliente;
        this.fechaInicio = LocalDate.now();
        this.fechaDevolucionPactada = fechaDevolucionPactada;
        this.estado = EstadoAlquiler.ACTIVO;
    }

    public void devolver(LocalDate fecha) {
        if (estado != EstadoAlquiler.ACTIVO) throw new IllegalStateException("El alquiler ya fue finalizado");
        fechaDevolucionReal = fecha;
        estado = EstadoAlquiler.FINALIZADO;
        vehiculo.liberar();
        if (fecha.isAfter(fechaDevolucionPactada)) {
            throw new DevolucionTardiaException(ChronoUnit.DAYS.between(fechaDevolucionPactada, fecha));
        }
    }

    public UUID getId() { return id; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public Cliente getCliente() { return cliente; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaDevolucionPactada() { return fechaDevolucionPactada; }
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public EstadoAlquiler getEstado() { return estado; }
}