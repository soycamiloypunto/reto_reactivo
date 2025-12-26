package com.pragma.tecnologia.infrastructure.entrypoints.dto;

import java.time.LocalDate;
import java.util.List;

public class BootcampRequest {
    private String nombre;
    private String descripcion;
    private LocalDate fechaLanzamiento; // HU7
    private Integer duracion;           // HU7
    private List<CapacidadRequest> capacidades; // Tu lista de capacidades

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
    public void setFechaLanzamiento(LocalDate fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public List<CapacidadRequest> getCapacidades() { return capacidades; }
    public void setCapacidades(List<CapacidadRequest> capacidades) { this.capacidades = capacidades; }
}