package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITecnologiaRequestMapper {

    // CAMBIO: Método default para resolver la ambigüedad manualmente
    default Tecnologia toDomain(TecnologiaRequest request) {
        if (request == null) return null;

        // Llamamos al constructor de 3 argumentos (ID nulo al crear)
        return new Tecnologia(
                null,
                request.getNombre(),
                request.getDescripcion()
        );
    }
}