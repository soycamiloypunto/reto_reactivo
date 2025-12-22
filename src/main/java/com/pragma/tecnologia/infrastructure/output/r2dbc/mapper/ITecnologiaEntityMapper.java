package com.pragma.tecnologia.infrastructure.output.r2dbc.mapper;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.TecnologiaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITecnologiaEntityMapper {
    Tecnologia toDomain(TecnologiaEntity entity);
}
