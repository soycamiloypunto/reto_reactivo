package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ICapacidadMapper {

    @Mapping(target = "tecnologias", source = "tecnologiasIds")
    Capacidad toDomain(CapacidadRequest request);

    default List<Tecnologia> mapIdsToTecnologias(List<Long> ids) {
        if (ids == null) return Collections.emptyList();
        return ids.stream()
                .map(id -> new Tecnologia(id)) // Llama al constructor de solo ID
                .toList();
    }

    @Mapping(target = "tecnologias", source = "tecnologias")
    CapacidadResponse toResponse(Capacidad capacidad);

    // Mapeo automático para la lista simplificada dentro del record
    CapacidadResponse.TecnologiaSimplificada map(Tecnologia tecnologia);
}
