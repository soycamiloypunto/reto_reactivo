package com.pragma.tecnologia.domain.model;

public class Tecnologia {
    private final Long id;
    private final String nombre;
    private final String descripcion;

    // ESTE ES EL CONSTRUCTOR QUE TE FALTA O ESTÁ MAL DEFINIDO
    public Tecnologia(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Constructor secundario (si lo necesitas para otros casos)
    public Tecnologia(Long id) {
        this.id = id;
        this.nombre = null;
        this.descripcion = null;
    }

    // Getters...
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
}