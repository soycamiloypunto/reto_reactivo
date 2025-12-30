package com.pragma.tecnologia.infrastructure.output.r2dbc.adapter;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.BootcampCapacidadEntity;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.CapacidadEntity;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.TecnologiaEntity;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.IBootcampEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.ICapacidadEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.ITecnologiaEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BootcampPersistenceAdapter implements IBootcampPersistencePort {
    private final IBootcampRepository IBootcampRepository;
    private final IBootcampEntityMapper IBootcampEntityMapper;
    private final IBootcampCapacidadRepository iBootcampCapacidadRepository;
    private final ICapacidadTecnologiaRepository icapacidadTecnologiaRepository;
    private final ICapacidadRepository iCapacidadRepository;
    private final ICapacidadEntityMapper iCapacidadEntityMapper;
    private final ITecnologiaRepository iTecnologiaRepository;
    private final ITecnologiaEntityMapper iTecnologiaEntityMapper;

    //HU4
    @Override
    public Mono<Bootcamp> guardarBootcamp(Bootcamp bootcamp) {
        return IBootcampRepository.save(IBootcampEntityMapper.toEntity(bootcamp))
                .flatMap(savedBootcampEntity -> {
                    // Guardamos relaciones intermedias
                    return Flux.fromIterable(bootcamp.getCapacidades())
                            .map(cap -> new BootcampCapacidadEntity(null, savedBootcampEntity.getId(), cap.getId()))
                            .flatMap(iBootcampCapacidadRepository::save)
                            .then(Mono.just(savedBootcampEntity)); // Retornamos la entidad guardada al flujo
                })
                // Convertimos de vuelta a Dominio para tener el ID
                .map(entity -> IBootcampEntityMapper.toDomainWithCapacities(entity, bootcamp.getCapacidades()));
    }

    //HU5
    @Override
    public Flux<Bootcamp> listarBootcamps(int page, int size, String sortField, String direction) {
        int offset = page * size;
        return IBootcampRepository.findAllCustom(sortField, direction, size, offset)
                .flatMap(bootcampEntity ->
                        // 1. Buscamos las capacidades de este bootcamp
                        iCapacidadRepository.findAllByBootcampId(bootcampEntity.getId())
                                .flatMap(capEntity ->
                                        // 2. Por cada capacidad, buscamos sus tecnologías
                                        iTecnologiaRepository.findAllByCapacidadId(capEntity.getId())
                                                .map(iTecnologiaEntityMapper::toDomain)
                                                .collectList()
                                                // 3. Mapeamos la capacidad con sus tecnologías
                                                .map(techs -> iCapacidadEntityMapper.toDomainWithTechs(capEntity, techs))
                                )
                                .collectList()
                                // 4. Mapeamos el bootcamp con sus capacidades completas
                                .map(caps -> IBootcampEntityMapper.toDomainWithCapacities(bootcampEntity, caps))
                );
    }

    private Flux<Capacidad> cargarCapacidadesConTecnologias(Long bootcampId) {
        return iCapacidadRepository.findAllByBootcampId(bootcampId)
                .flatMap(capEntity ->
                        iTecnologiaRepository.findAllByCapacidadId(capEntity.getId())
                                .map(iTecnologiaEntityMapper::toDomain)
                                .collectList()
                                .map(techs -> iCapacidadEntityMapper.toDomainWithTechs(capEntity, techs))
                );
    }


    //HU6
    @Override
    @Transactional
    public Mono<Void> eliminarBootcamp(Long bootcampId) {
        // Solo borramos la relación directa y el Bootcamp.
        // La lógica de "qué pasa con las capacidades" se movió al UseCase.
        return iBootcampCapacidadRepository.deleteAllByBootcampId(bootcampId)
                .then(IBootcampRepository.deleteById(bootcampId));
    }

    // Puedes reusar 'obtenerBootcampCompleto' o crear uno ligero:
    public Flux<Capacidad> obtenerCapacidadesDelBootcamp(Long bootcampId) {
        return iCapacidadRepository.findAllByBootcampId(bootcampId)
                .map(iCapacidadEntityMapper::toDomainSimple); // Sin tecnologías para ser más rápido
    }

    public Mono<Bootcamp> obtenerBootcampPorId(Long id) {
        return IBootcampRepository.findById(id)
                .map(IBootcampEntityMapper::toDomainSimple);
    }

    //HU8
    @Override
    public Mono<Bootcamp> obtenerBootcampCompleto(Long id) {
        return IBootcampRepository.findById(id)
                .flatMap(bootcampEntity -> cargarCapacidadesConTecnologias(bootcampEntity.getId())
                        .collectList()
                        .map(caps -> IBootcampEntityMapper.toDomainWithCapacities(bootcampEntity, caps))
                );
    }
}




