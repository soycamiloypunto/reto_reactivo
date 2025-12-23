package com.pragma.tecnologia.infrastructure.entrypoints.dto;

import java.util.List;

public record CapacidadSimpleResponse(
        Long id,
        String nombre,
        List<TecnologiaSimpleResponse> tecnologias
) {}

