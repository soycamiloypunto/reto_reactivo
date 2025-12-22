package com.pragma.tecnologia.infrastructure.entrypoints.dto;

import java.util.List;

public record CapacidadResponse(
        Long id,
        String nombre,
        String descripcion,
        List<TecnologiaSimplificada> tecnologias
) {
    public record TecnologiaSimplificada(Long id, String nombre) {}
}
