package com.pragma.tecnologia.infrastructure.output.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("bootcamp")
public class BootcampEntity {

    @Id
    private Long id;

    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;

    @Column("fecha_lanzamiento")
    private LocalDate fechaLanzamiento;

    @Column("duracion")
    private Integer duracion;

    public BootcampEntity() {}

    public BootcampEntity(Long id, String nombre, String descripcion, LocalDate fechaLanzamiento, Integer duracion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaLanzamiento = fechaLanzamiento;
        this.duracion = duracion;
    }

    // --- GETTERS Y SETTERS MANUALES ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaLanzamiento() { return fechaLanzamiento; }
    public void setFechaLanzamiento(LocalDate fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }
}