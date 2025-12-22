package com.pragma.tecnologia.infrastructure.output.r2dbc.mapper;

import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.CapacidadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacidadEntityMapper {
    CapacidadEntity toEntity(Capacidad capacidad);
    Capacidad toDomain(CapacidadEntity entity);

    @Mapping(target = "tecnologias", source = "tecnologias")
    Capacidad toDomainWithTechs(CapacidadEntity entity, List<Tecnologia> tecnologias);
}
