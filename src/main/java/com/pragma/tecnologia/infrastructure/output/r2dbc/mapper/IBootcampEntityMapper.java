package com.pragma.tecnologia.infrastructure.output.r2dbc.mapper;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.BootcampEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampEntityMapper {

    @Mapping(target = "id", ignore = true)
    BootcampEntity toEntity(Bootcamp bootcamp);

    // CAMBIO: Ignorar capacidades porque la BootcampEntity no tiene esa lista
    @Mapping(target = "capacidades", ignore = true)
    Bootcamp toDomain(BootcampEntity entity);

    default Bootcamp toDomainWithCapacities(BootcampEntity entity, List<Capacidad> capacidades) {
        if (entity == null) return null;
        return new Bootcamp(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getFechaLanzamiento(),
                entity.getDuracion(),
                capacidades
        );
    }
}