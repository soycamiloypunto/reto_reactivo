package com.pragma.tecnologia.infrastructure.entrypoints.dto;

import java.util.List;

public record BootcampResponse(
        Long id,
        String nombre,
        String descripcion,
        List<CapacidadSimpleResponse> capacidades
) {}

