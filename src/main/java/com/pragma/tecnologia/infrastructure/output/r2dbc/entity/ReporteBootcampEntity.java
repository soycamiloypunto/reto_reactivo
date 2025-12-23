package com.pragma.tecnologia.infrastructure.output.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDate;

@Table("reporte_bootcamp")
public class ReporteBootcampEntity {
    @Id
    private Long id;
    private Long bootcampId;
    private String nombreBootcamp;
    private Integer cantidadCapacidades;
    private Integer cantidadTecnologias;
    private Integer cantidadInscritos;
    private LocalDate fechaRegistro;

    public ReporteBootcampEntity(Long id, Long bootcampId, String nombreBootcamp, Integer cantidadCapacidades, Integer cantidadTecnologias, Integer cantidadInscritos, LocalDate fechaRegistro) {
        this.id = id;
        this.bootcampId = bootcampId;
        this.nombreBootcamp = nombreBootcamp;
        this.cantidadCapacidades = cantidadCapacidades;
        this.cantidadTecnologias = cantidadTecnologias;
        this.cantidadInscritos = cantidadInscritos;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters (omito setters por brevedad, usa Lombok @Data si prefieres)
}