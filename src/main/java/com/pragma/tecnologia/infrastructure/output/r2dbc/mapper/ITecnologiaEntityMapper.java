package com.pragma.tecnologia.infrastructure.output.r2dbc.mapper;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.TecnologiaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITecnologiaEntityMapper {
    TecnologiaEntity toEntity(Tecnologia tecnologia);

    // Método default para evitar ambigüedad de constructores
    default Tecnologia toDomain(TecnologiaEntity entity) {
        if (entity == null) return null;
        return new Tecnologia(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion()
        );
    }
}
