package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITecnologiaRequestMapper {
    // Mapea el DTO de entrada al modelo de dominio para aplicar reglas de negocio
    @Mapping(target = "id", ignore = true)
    Tecnologia toDomain(TecnologiaRequest tecnologiaRequest);
}
