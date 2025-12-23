package com.pragma.tecnologia.infrastructure.output.r2dbc.adapter;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort;
import com.pragma.tecnologia.domain.spi.ICapacidadPersistencePort;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.BootcampCapacidadEntity;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.IBootcampEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.ICapacidadEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.ITecnologiaEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.IBootcampCapacidadRepository;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.IBootcampRepository;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.ICapacidadRepository;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.ITecnologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BootcampPersistenceAdapter implements IBootcampPersistencePort {
    private final IBootcampRepository IBootcampRepository;
    private final IBootcampEntityMapper IBootcampEntityMapper;
    private final IBootcampCapacidadRepository relacionRepository;
    private final ICapacidadRepository iCapacidadRepository;
    private final ICapacidadEntityMapper iCapacidadEntityMapper;
    private final ITecnologiaRepository iTecnologiaRepository;
    private final ITecnologiaEntityMapper iTecnologiaEntityMapper;

    @Override
    public Mono<Void> guardarBootcamp(Bootcamp bootcamp) {
        return IBootcampRepository.save(IBootcampEntityMapper.toEntity(bootcamp))
                .flatMapMany(savedBootcamp -> Flux.fromIterable(bootcamp.getCapacidades())
                        .map(cap -> new BootcampCapacidadEntity(null, savedBootcamp.getId(), cap.getId())))
                .flatMap(relacionRepository::save)
                .then();
    }

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

}




