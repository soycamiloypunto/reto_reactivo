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

    // CAMBIO: Convertido a default para resolver ambigüedad manualmente
    default Bootcamp toDomain(BootcampEntity entity) {
        if (entity == null) return null;
        // Usamos el Constructor 2 (Sin lista, sin validación estricta)
        return new Bootcamp(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getFechaLanzamiento(),
                entity.getDuracion()
        );
    }

    // Mapeo completo (HU4/HU5) - Usa Constructor 1
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

    // Mapeo simple para validaciones (HU7) - Usa Constructor 2
    // Nota: Es idéntico a toDomain ahora, pero lo mantenemos por claridad semántica
    default Bootcamp toDomainSimple(BootcampEntity entity) {
        if (entity == null) return null;
        return new Bootcamp(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getFechaLanzamiento(),
                entity.getDuracion()
        );
    }
}