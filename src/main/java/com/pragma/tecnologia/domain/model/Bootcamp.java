package com.pragma.tecnologia.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bootcamp {
    private final Long id;
    private final String nombre;
    private final String descripcion;
    private final LocalDate fechaLanzamiento;
    private final Integer duracion;
    private final List<Capacidad> capacidades;

    // CONSTRUCTOR PRINCIPAL (Solo asignación)
    public Bootcamp(Long id, String nombre, String descripcion,
                    LocalDate fechaLanzamiento, Integer duracion,
                    List<Capacidad> capacidades) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaLanzamiento = fechaLanzamiento;
        this.duracion = duracion;
        this.capacidades = capacidades;
    }

    // CONSTRUCTOR AUXILIAR (Para lógica interna o tests si es necesario)
    // Inicializa capacidades vacías para evitar NullPointer
    public Bootcamp(Long id, String nombre, String descripcion,
                    LocalDate fechaLanzamiento, Integer duracion) {
        this(id, nombre, descripcion, fechaLanzamiento, duracion, new ArrayList<>());
    }

    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
    public Integer getDuracion() { return duracion; }
    public List<Capacidad> getCapacidades() { return capacidades; }
}