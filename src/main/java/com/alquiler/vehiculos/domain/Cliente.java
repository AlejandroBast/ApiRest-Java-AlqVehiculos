package com.alquiler.vehiculos.domain;

import java.time.LocalDate;
import java.util.UUID;

import com.alquiler.vehiculos.exception.LicenciaInvalidaException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {
    @Id @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String documento;

    // fecha hasta la que vale la licencia; null si no tiene
    private LocalDate licenciaVence;

    protected Cliente() {}

    public Cliente(String nombre, String documento, LocalDate licenciaVence) {
        exigir(nombre, "nombre");
        exigir(documento, "documento");
        this.nombre = nombre;
        this.documento = documento.trim();
        this.licenciaVence = licenciaVence;
    }

    public void actualizar(String nombre, LocalDate licenciaVence) {
        exigir(nombre, "nombre");
        this.nombre = nombre;
        this.licenciaVence = licenciaVence;
    }

    public void validarLicencia() {
        if (licenciaVence == null || licenciaVence.isBefore(LocalDate.now())) {
            throw new LicenciaInvalidaException(nombre);
        }
    }

    private static void exigir(String valor, String campo) {
        if (valor == null || valor.isBlank()) throw new IllegalArgumentException(campo + " obligatorio");
    }

    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDocumento() { return documento; }
    public LocalDate getLicenciaVence() { return licenciaVence; }
}