package com.pragma.tecnologia.infrastructure.output.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("bootcamp_capacidad")
public class BootcampCapacidadEntity {

    @Id
    private Long id;

    @Column("bootcamp_id")
    private Long bootcampId;

    @Column("capacidad_id")
    private Long capacidadId;

    public BootcampCapacidadEntity() {}

    public BootcampCapacidadEntity(Long id, Long bootcampId, Long capacidadId) {
        this.id = id;
        this.bootcampId = bootcampId;
        this.capacidadId = capacidadId;
    }

    // --- GETTERS Y SETTERS MANUALES ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBootcampId() { return bootcampId; }
    public void setBootcampId(Long bootcampId) { this.bootcampId = bootcampId; }

    public Long getCapacidadId() { return capacidadId; }
    public void setCapacidadId(Long capacidadId) { this.capacidadId = capacidadId; }
}