package com.pragma.tecnologia.domain.model;

import com.pragma.tecnologia.domain.exceptions.DomainException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList; // Necesario importar

public class Bootcamp {
    private final Long id;
    private final String nombre;
    private final String descripcion;
    private final LocalDate fechaLanzamiento;
    private final Integer duracion;
    private final List<Capacidad> capacidades;

    // CONSTRUCTOR 1: El que ya tienes (Validación estricta para CREAR)
    public Bootcamp(Long id, String nombre, String descripcion,
                    LocalDate fechaLanzamiento, Integer duracion,
                    List<Capacidad> capacidades) {
        if (capacidades == null || capacidades.isEmpty() || capacidades.size() > 4) {
            throw new DomainException("Un bootcamp debe tener entre 1 y 4 capacidades.");
        }
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaLanzamiento = fechaLanzamiento;
        this.duracion = duracion;
        this.capacidades = capacidades;
    }

    // --- AGREGAR ESTE NUEVO CONSTRUCTOR ---
    // CONSTRUCTOR 2: Para VALIDACIONES (HU7).
    // Inicializa la lista vacía pero NO lanza el error.
    public Bootcamp(Long id, String nombre, String descripcion,
                    LocalDate fechaLanzamiento, Integer duracion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaLanzamiento = fechaLanzamiento;
        this.duracion = duracion;
        this.capacidades = new ArrayList<>(); // Lista vacía segura, SIN VALIDACIÓN
    }
    // --------------------------------------

    // Getters...
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
    public Integer getDuracion() { return duracion; }
    public List<Capacidad> getCapacidades() { return capacidades; }
}