package com.pragma.tecnologia.infrastructure.output.r2dbc.adapter;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort;
import com.pragma.tecnologia.domain.spi.ICapacidadPersistencePort;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.BootcampCapacidadEntity;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.IBootcampEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.IBootcampCapacidadRepository;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.IBootcampRepository;
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

    @Override
    public Mono<Void> guardarBootcamp(Bootcamp bootcamp) {
        return IBootcampRepository.save(IBootcampEntityMapper.toEntity(bootcamp))
                .flatMapMany(savedBootcamp -> Flux.fromIterable(bootcamp.getCapacidades())
                        .map(cap -> new BootcampCapacidadEntity(null, savedBootcamp.getId(), cap.getId())))
                .flatMap(relacionRepository::save)
                .then();
    }

}




