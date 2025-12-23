package com.pragma.tecnologia.domain.model;

import com.pragma.tecnologia.domain.exceptions.DomainException;
import java.util.List;

public class Capacidad {
    private final Long id;
    private final String nombre;
    private final String descripcion;
    private final List<Tecnologia> tecnologias;

    // CONSTRUCTOR 1: Para LISTAR y CREAR (Con todas las validaciones)
    public Capacidad(Long id, String nombre, String descripcion, List<Tecnologia> tecnologias) {
        if (nombre == null || nombre.length() > 50) {
            throw new DomainException("El nombre no debe exceder los 50 caracteres");
        }
        if (descripcion == null || descripcion.length() > 90) {
            throw new DomainException("La descripción no debe exceder los 90 caracteres");
        }
        if (tecnologias == null || tecnologias.size() < 3 || tecnologias.size() > 20) {
            throw new DomainException("La capacidad debe tener entre 3 y 20 tecnologías");
        }
        if (tieneTecnologiasRepetidas(tecnologias)) {
            throw new DomainException("No se permiten tecnologías repetidas en la capacidad");
        }
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tecnologias = tecnologias;
    }

    // CONSTRUCTOR 2: Para REFERENCIA (Solo ID, usado en HU4 Bootcamp)
    public Capacidad(Long id) {
        this.id = id;
        this.nombre = null;
        this.descripcion = null;
        this.tecnologias = null;
    }

    private boolean tieneTecnologiasRepetidas(List<Tecnologia> lista) {
        return lista.stream()
                .map(Tecnologia::getId)
                .distinct()
                .count() != lista.size();
    }

    // Getters...
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public List<Tecnologia> getTecnologias() { return tecnologias; }
}