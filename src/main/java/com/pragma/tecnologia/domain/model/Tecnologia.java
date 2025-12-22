package com.pragma.tecnologia.domain.model;

import com.pragma.tecnologia.domain.exceptions.DomainException;

public class Tecnologia {
    private final Long id;
    private final String nombre;
    private final String descripcion;

    // Constructor de referencia (HU2)
    public Tecnologia(Long id) {
        this.id = id;
        this.nombre = null;
        this.descripcion = null;
    }

    @Default // <--- ESTA ANOTACIÓN RESUELVE LA AMBIGÜEDAD
    public Tecnologia(Long id, String nombre, String descripcion) {
        // ... tus validaciones de la HU1 permanecen intactas
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // ... getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}