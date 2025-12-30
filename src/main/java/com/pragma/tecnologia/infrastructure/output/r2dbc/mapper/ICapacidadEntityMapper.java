package com.pragma.tecnologia.infrastructure.output.r2dbc.mapper;

import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.CapacidadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacidadEntityMapper {

    @Mapping(target = "id", ignore = true)
    CapacidadEntity toEntity(Capacidad capacidad);

    // Implementación default para evitar ambigüedad en toDomain
    default Capacidad toDomain(CapacidadEntity entity) {
        if (entity == null) return null;
        return new Capacidad(entity.getId()); // Usa el constructor de referencia
    }

    // Mapeo detallado con el constructor completo
    default Capacidad toDomainWithTechs(CapacidadEntity entity, List<Tecnologia> tecnologias) {
        if (entity == null) return null;
        return new Capacidad(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                tecnologias
        );
    }

    // Mapea solo los datos básicos de la entidad al dominio, con lista de tecnologías vacía.
    default Capacidad toDomainSimple(CapacidadEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Capacidad(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                Collections.emptyList() // ¡Importante! Lista vacía segura
        );
    }
}
