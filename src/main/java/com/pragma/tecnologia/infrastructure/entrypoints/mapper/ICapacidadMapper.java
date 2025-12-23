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

    // CAMBIO: Se usa un método default para elegir el constructor de 4 argumentos
    default Capacidad toDomain(CapacidadRequest request) {
        if (request == null) return null;

        return new Capacidad(
                null, // ID nulo para creación
                request.getNombre(),
                request.getDescripcion(),
                mapIdsToTecnologias(request.getTecnologiasIds())
        );
    }

    default List<Tecnologia> mapIdsToTecnologias(List<Long> ids) {
        if (ids == null) return Collections.emptyList();
        return ids.stream()
                .map(Tecnologia::new)
                .toList();
    }

    @Mapping(target = "tecnologias", source = "tecnologias")
    CapacidadResponse toResponse(Capacidad capacidad);

    CapacidadResponse.TecnologiaSimplificada map(Tecnologia tecnologia);
}