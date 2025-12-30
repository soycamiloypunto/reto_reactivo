package com.pragma.tecnologia.domain.model;

import java.util.List;

public class Capacidad {
    private final Long id;
    private final String nombre;
    private final String descripcion;
    private final List<Tecnologia> tecnologias;

    // CONSTRUCTOR 1: Completo
    public Capacidad(Long id, String nombre, String descripcion, List<Tecnologia> tecnologias) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tecnologias = tecnologias;
    }

    // CONSTRUCTOR 2: Referencia (Solo ID)
    public Capacidad(Long id) {
        this.id = id;
        this.nombre = null;
        this.descripcion = null;
        this.tecnologias = null;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public List<Tecnologia> getTecnologias() { return tecnologias; }
}