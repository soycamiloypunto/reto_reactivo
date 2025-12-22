package com.pragma.tecnologia.domain.model;

import com.pragma.tecnologia.domain.exceptions.DomainException;

import java.util.List;

public class Capacidad {
    private final Long id;
    private final String nombre;
    private final String descripcion;
    private final List<Tecnologia> tecnologias;


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

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Tecnologia> getTecnologias() {
        return tecnologias;
    }
}