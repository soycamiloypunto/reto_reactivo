package com.pragma.tecnologia.infrastructure.output.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("matricula")
public class MatriculaEntity {

    @Id
    private Long id;
    private Long idUsuario; // ID del usuario que viene del Token/Request
    private Long idBootcamp; // ID del bootcamp al que se inscribe

    public MatriculaEntity(Long id, Long idUsuario, Long idBootcamp) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idBootcamp = idBootcamp;
    }

    // Getters
    public Long getId() { return id; }
    public Long getIdUsuario() { return idUsuario; }
    public Long getIdBootcamp() { return idBootcamp; }
}