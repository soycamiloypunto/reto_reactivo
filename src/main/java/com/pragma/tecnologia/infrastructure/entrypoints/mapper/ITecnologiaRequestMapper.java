package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITecnologiaRequestMapper {

    // Usamos 'default' para resolver la ambigüedad del constructor manualmente.
    // Esto actúa como una "Factory" simple.
    default Tecnologia toDomain(TecnologiaRequest request) {
        if (request == null) {
            return null;
        }

        // Aquí elegimos explícitamente el constructor de 3 argumentos
        return new Tecnologia(
                null, // El ID es null porque es una creación
                request.getNombre(),
                request.getDescripcion()
        );
    }
}