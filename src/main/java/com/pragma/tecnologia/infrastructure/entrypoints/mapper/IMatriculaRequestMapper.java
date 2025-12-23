package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Matricula;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.MatriculaRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IMatriculaRequestMapper {

    @Mapping(target = "id", ignore = true) // El ID de la matrícula es null al crear
    Matricula toDomain(MatriculaRequest request);
}