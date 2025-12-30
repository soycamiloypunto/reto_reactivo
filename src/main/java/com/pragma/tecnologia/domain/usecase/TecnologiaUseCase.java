package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.ITecnologiaServicePort;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.ITecnologiaPersistencePort;
import com.pragma.tecnologia.domain.util.TecnologiaValidator;
import reactor.core.publisher.Mono;

public class TecnologiaUseCase implements ITecnologiaServicePort {

    private final ITecnologiaPersistencePort tecnologiaPersistencePort;

    public TecnologiaUseCase(ITecnologiaPersistencePort tecnologiaPersistencePort) {
        this.tecnologiaPersistencePort = tecnologiaPersistencePort;
    }

    //HU1
    @Override
    public Mono<Void> registrarTecnologia(Tecnologia tecnologia) {
        // 1. Validación de campos (Síncrona)
        TecnologiaValidator.validar(tecnologia);

        // 2. Validación de negocio y persistencia (Reactiva)
        return tecnologiaPersistencePort.existePorNombre(tecnologia.getNombre())
                .flatMap(existe -> {
                    if (Boolean.TRUE.equals(existe)) {
                        return Mono.error(new DomainException("La tecnología ya existe"));
                    }
                    return tecnologiaPersistencePort.guardar(tecnologia);
                }).then();
    }
}