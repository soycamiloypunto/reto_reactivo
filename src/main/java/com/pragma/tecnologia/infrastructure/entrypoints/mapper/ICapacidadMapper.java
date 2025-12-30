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

    // 1. TO DOMAIN: Manual para elegir constructor y mapear la lista de IDs
    default Capacidad toDomain(CapacidadRequest request) {
        if (request == null) return null;

        // OJO: Asegúrate de usar el getter correcto de tu DTO (getTecnologias o getTecnologiasIds)
        List<Tecnologia> listaTecnologias = mapIdsToTecnologias(request.getTecnologiasIds());

        return new Capacidad(
                null, // ID nulo para creación
                request.getNombre(),
                request.getDescripcion(),
                listaTecnologias
        );
    }

    // Método auxiliar para convertir [1, 2, 3] -> [Tecnologia(1), Tecnologia(2)...]
    default List<Tecnologia> mapIdsToTecnologias(List<Long> ids) {
        if (ids == null) return Collections.emptyList();
        return ids.stream()
                // Esto funciona porque Tecnologia tiene el constructor public Tecnologia(Long id)
                .map(Tecnologia::new)
                .toList();
    }

    // 2. TO RESPONSE: Automático con soporte para la clase interna simplificada
    @Mapping(target = "tecnologias", source = "tecnologias")
    CapacidadResponse toResponse(Capacidad capacidad);

    // Mapeo automático de Tecnologia -> TecnologiaSimplificada (Record o Clase interna)
    CapacidadResponse.TecnologiaSimplificada map(Tecnologia tecnologia);
}