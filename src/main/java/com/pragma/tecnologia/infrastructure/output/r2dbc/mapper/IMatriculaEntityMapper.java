package com.pragma.tecnologia.infrastructure.output.r2dbc.mapper;

import com.pragma.tecnologia.domain.model.Matricula;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.MatriculaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IMatriculaEntityMapper {

    @Mapping(target = "id", ignore = true) // El ID se genera automático al guardar
    MatriculaEntity toEntity(Matricula matricula);

    Matricula toDomain(MatriculaEntity entity);
}