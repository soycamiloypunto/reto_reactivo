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
    private final ICapacidadTecnologiaRepository relacionRepository; // Para borrar relaciones
    private final ICapacidadEntityMapper capacidadEntityMapper;
    private final ITecnologiaRepository iTecnologiaRepository;
    private final ITecnologiaEntityMapper iTecnologiaEntityMapper;

    @Override
    public Mono<Capacidad> guardar(Capacidad capacidad) {
        return capacidadRepository.save(capacidadEntityMapper.toEntity(capacidad))
                .flatMap(savedEntity -> {
                    List<CapacidadTecnologiaEntity> relaciones = capacidad.getTecnologias().stream()
                            .map(tec -> new CapacidadTecnologiaEntity(savedEntity.getId(), tec.getId()))
                            .toList();

                    return relacionRepository.saveAll(relaciones)
                            .then(Mono.just(capacidad));
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
                                .filter(t -> t.getId() != null)
                                .distinct(Tecnologia::getId)
                                .collectList()
                                .map(techs -> capacidadEntityMapper.toDomainWithTechs(entity, techs))
                );
    }

    // --- NUEVOS MÉTODOS PARA SOPORTAR HU6 (Eliminación en Cascada) ---
    @Override
    public Mono<Long> contarUsosEnBootcamps(Long capacidadId) {
        // Cuenta en la tabla intermedia 'bootcamp_capacidad'
        return capacidadRepository.countBootcampsByCapacidadId(capacidadId);
    }

    @Override
    public Flux<Tecnologia> obtenerTecnologiasPorCapacidad(Long capacidadId) {
        // Reutilizamos la lógica de búsqueda de tecnologías
        return iTecnologiaRepository.findAllByCapacidadId(capacidadId)
                .map(iTecnologiaEntityMapper::toDomain);
    }

    @Override
    public Mono<Void> eliminarCapacidad(Long capacidadId) {
        // 1. Borrar relaciones en 'capacidad_tecnologia'
        return relacionRepository.deleteAllByCapacidadId(capacidadId)
                // 2. Borrar la entidad 'capacidad'
                .then(capacidadRepository.deleteById(capacidadId));
    }
}