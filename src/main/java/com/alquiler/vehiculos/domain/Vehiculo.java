package com.alquiler.vehiculos.domain;

import java.util.UUID;

import com.alquiler.vehiculos.exception.VehiculoNoDisponibleException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {
    @Id @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private String modelo;

    @Enumerated(EnumType.STRING)
    private EstadoVehiculo estado;

    protected Vehiculo() {}

    public Vehiculo(String placa, String marca, String modelo) {
        exigir(placa, "placa");
        exigir(marca, "marca");
        exigir(modelo, "modelo");
        this.placa = placa.trim().toUpperCase();
        this.marca = marca;
        this.modelo = modelo;
        this.estado = EstadoVehiculo.DISPONIBLE;
    }

    public void actualizar(String marca, String modelo) {
        exigir(marca, "marca");
        exigir(modelo, "modelo");
        this.marca = marca;
        this.modelo = modelo;
    }

    public void alquilar() {
        if (estado != EstadoVehiculo.DISPONIBLE) throw new VehiculoNoDisponibleException(placa);
        estado = EstadoVehiculo.ALQUILADO;
    }

    public void liberar() {
        estado = EstadoVehiculo.DISPONIBLE;
    }

    private static void exigir(String valor, String campo) {
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException(campo + " obligatorio");
    }

    public UUID getId() { return id; }
    public String getPlaca() { return placa; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public EstadoVehiculo getEstado() { return estado; }
}