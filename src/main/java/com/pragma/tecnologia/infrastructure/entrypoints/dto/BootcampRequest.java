package com.pragma.tecnologia.infrastructure.entrypoints.dto;

import java.time.LocalDate;
import java.util.List;

public record BootcampRequest(
        String nombre,           // Debe llamarse "nombre" en el JSON
        String descripcion,
        LocalDate fechaLanzamiento,
        Integer duracion,
        List<Long> capacidadesIds
) {}