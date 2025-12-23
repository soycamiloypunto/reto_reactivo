package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBootcampResponseMapper {
    BootcampResponse toResponse(Bootcamp bootcamp);

    // Mapstruct mapeará automáticamente las listas si los nombres coinciden
}
