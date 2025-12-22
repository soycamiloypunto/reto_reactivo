package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IBootcampMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "capacidades", source = "capacidadesIds", qualifiedByName = "mapIdsToCapacidades")
    Bootcamp toDomain(BootcampRequest request);

    @Named("mapIdsToCapacidades")
    default List<Capacidad> mapIdsToCapacidades(List<Long> ids) {
        if (ids == null) return new ArrayList<>();
        return ids.stream()
                .map(Capacidad::new) // <--- Llama al nuevo constructor que NO tiene validaciones
                .toList();
    }
}