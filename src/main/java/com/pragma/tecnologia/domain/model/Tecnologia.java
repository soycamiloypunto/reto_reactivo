package com.pragma.tecnologia.domain.model;

import com.pragma.tecnologia.domain.exceptions.DomainException;

public class Tecnologia {
    private final Long id;
    private final String nombre;
    private final String descripcion;

    // Constructor Principal (Con Validaciones HU1)
    public Tecnologia(Long id, String nombre, String descripcion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DomainException("El nombre es obligatorio");
        }
        if (nombre.length() > 50) {
            throw new DomainException("El nombre no puede exceder los 50 caracteres");
        }
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new DomainException("La descripción es obligatoria");
        }
        if (descripcion.length() > 90) {
            throw new DomainException("La descripción no puede exceder los 90 caracteres");
        }
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Constructor de referencia (Solo ID, para HU2/HU4)
    public Tecnologia(Long id) {
        this.id = id;
        this.nombre = null;
        this.descripcion = null;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}