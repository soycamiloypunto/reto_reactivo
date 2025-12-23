package com.pragma.tecnologia.infrastructure.output.r2dbc.adapter;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.ITecnologiaPersistencePort;
import com.pragma.tecnologia.infrastructure.output.r2dbc.mapper.ITecnologiaEntityMapper;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.ITecnologiaRepository;
import lombok.RequiredArgsConstructor; // 1. Importar Lombok
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor // 2. Genera el constructor para todos los campos 'final'
public class TecnologiaPersistenceAdapter implements ITecnologiaPersistencePort {

    // 3. Declarar solo una vez cada dependencia
    private final ITecnologiaRepository iTecnologiaRepository;
    private final ITecnologiaEntityMapper iTecnologiaEntityMapper;

    // ELIMINADO: El constructor manual ya no es necesario gracias a @RequiredArgsConstructor

    @Override
    public Mono<Tecnologia> guardar(Tecnologia tecnologia) {
        return Mono.just(tecnologia)
                .map(iTecnologiaEntityMapper::toEntity)
                .flatMap(iTecnologiaRepository::save)
                .map(iTecnologiaEntityMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existePorNombre(String nombre) {
        return iTecnologiaRepository.existsByNombre(nombre); // Usa la variable correcta
    }
}