package com.pragma.tecnologia.infrastructure.output.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Table("capacidad_tecnologia")
@Getter
@Setter
@AllArgsConstructor
public class CapacidadTecnologiaEntity {
    private Long capacidadId;
    private Long tecnologiaId;
}
