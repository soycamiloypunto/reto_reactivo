package com.pragma.tecnologia.domain.model;

import com.pragma.tecnologia.domain.exceptions.DomainException;

import java.time.LocalDate;
import java.util.List;

public class Bootcamp {
    private final Long id;
    private final String nombre;
    private final String descripcion;
    private final LocalDate fechaLanzamiento; // Nuevo
    private final Integer duracion;           // Nuevo (en días o semanas)
    private final List<Capacidad> capacidades;

    public Bootcamp(Long id, String nombre, String descripcion,
                    LocalDate fechaLanzamiento, Integer duracion,
                    List<Capacidad> capacidades) {
        // Regla de negocio: Mínimo 1, máximo 4 capacidades
        if (capacidades == null || capacidades.isEmpty() || capacidades.size() > 4) {
            throw new DomainException("Un bootcamp debe tener entre 1 y 4 capacidades.");
        }
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaLanzamiento=fechaLanzamiento;
        this.duracion=duracion;
        this.capacidades = capacidades;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public List<Capacidad> getCapacidades() {
        return capacidades;
    }
}