package com.pragma.tecnologia.infrastructure.entrypoints.mapper;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadRequest;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IBootcampMapper {

    // 1. Método principal: Convierte el Request a Dominio
    default Bootcamp toDomain(BootcampRequest request) {
        if (request == null) return null;

        // Convertimos manualmente la lista de capacidades
        List<Capacidad> capacidades = mapCapacidades(request.getCapacidades());

        // Usamos el Constructor 1 de Bootcamp (El completo con validaciones)
        return new Bootcamp(
                null, // ID nulo al crear
                request.getNombre(),
                request.getDescripcion(),
                request.getFechaLanzamiento(),
                request.getDuracion(),
                capacidades // Pasamos la lista ya convertida
        );
    }

    // 2. Método auxiliar manual: Convierte la lista de DTOs a lista de Dominio
    // Esto evita que MapStruct intente hacerlo automático y falle por los constructores de Capacidad
    default List<Capacidad> mapCapacidades(List<CapacidadRequest> requests) {
        if (requests == null) return new ArrayList<>();

        return requests.stream()
                .map(req -> {
                    // AQUÍ resolvemos la ambigüedad de Capacidad.
                    // Asumimos que al crear un Bootcamp, envías el ID de capacidades existentes.
                    // Usamos el constructor de referencia: new Capacidad(Long id)
                    return new Capacidad(req.getId());
                })
                .collect(Collectors.toList());
    }
}