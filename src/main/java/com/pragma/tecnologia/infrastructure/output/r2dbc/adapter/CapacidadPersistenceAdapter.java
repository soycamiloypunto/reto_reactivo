package com.pragma.tecnologia.infrastructure.output.r2dbc.adapter;

import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.ICapacidadPersistencePort;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.CapacidadTecnologiaEntity;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.ICapacidadEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.ITecnologiaEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.ICapacidadRepository;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.ICapacidadTecnologiaRepository;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.ITecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CapacidadPersistenceAdapter implements ICapacidadPersistencePort {

    private final ICapacidadRepository capacidadRepository;
    private final ICapacidadTecnologiaRepository relacionRepository; // Inyectar nuevo repo
    private final ICapacidadEntityMapper capacidadEntityMapper;
    private final ITecnologiaRepository iTecnologiaRepository;
    private final ITecnologiaEntityMapper iTecnologiaEntityMapper;

    @Override
    public Mono<Capacidad> guardar(Capacidad capacidad) {
        return capacidadRepository.save(capacidadEntityMapper.toEntity(capacidad))
                .flatMap(savedEntity -> {
                    // 1. Creamos las entidades de relación usando el ID de la capacidad guardada
                    List<CapacidadTecnologiaEntity> relaciones = capacidad.getTecnologias().stream()
                            .map(tec -> new CapacidadTecnologiaEntity(savedEntity.getId(), tec.getId()))
                            .toList();

                    // 2. Guardamos todas las relaciones y al final devolvemos el dominio original
                    return relacionRepository.saveAll(relaciones)
                            .then(Mono.just(capacidad)); // Retornamos el objeto original que SI tiene las tecnologías
                });
    }

    @Override
    public Mono<Boolean> existePorNombre(String nombre) {
        return capacidadRepository.existsByNombre(nombre);
    }

    @Override
    public Flux<Capacidad> listarCapacidades(int page, int size, String sortField, String direction) {
        int offset = page * size;
        return capacidadRepository.findAllCustom(sortField, direction, size, offset)
                .flatMap(entity ->
                        iTecnologiaRepository.findAllByCapacidadId(entity.getId())
                                .map(iTecnologiaEntityMapper::toDomain)
                                .filter(t -> t.getId() != null) // <--- 1. FILTRO DE SEGURIDAD (Agrega esto)
                                // referencia al ID para el distinct
                                .distinct(Tecnologia::getId) // <--- 2. AHORA SÍ ES SEGURO
                                .collectList()
                                .map(techs -> capacidadEntityMapper.toDomainWithTechs(entity, techs))
                );
    }
}