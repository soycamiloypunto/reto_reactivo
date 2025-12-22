package com.pragma.tecnologia.infrastructure.output.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("capacidad")
public class CapacidadEntity {

    @Id
    private Long id;

    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;

    // --- CONSTRUCTORES MANUALES ---

    public CapacidadEntity() {
    }

    public CapacidadEntity(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // --- GETTERS Y SETTERS MANUALES (Necesarios para R2DBC) ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}