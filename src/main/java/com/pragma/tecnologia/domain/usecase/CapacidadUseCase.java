package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.ICapacidadServicePort;
import com.pragma.tecnologia.domain.exceptions.DomainError;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.spi.ICapacidadPersistencePort;
import com.pragma.tecnologia.domain.util.CapacidadValidator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CapacidadUseCase implements ICapacidadServicePort {

    private final ICapacidadPersistencePort capacidadPersistencePort;

    public CapacidadUseCase(ICapacidadPersistencePort capacidadPersistencePort) {
        this.capacidadPersistencePort = capacidadPersistencePort;
    }

    @Override
    public Mono<Void> registrarCapacidad(Capacidad capacidad) {
        // 1. Validaciones de Dominio (Síncronas)
        CapacidadValidator.validar(capacidad);

        // 2. Lógica Reactiva (BD)
        return capacidadPersistencePort.existePorNombre(capacidad.getNombre())
                .flatMap(existe -> {
                    if (Boolean.TRUE.equals(existe)) {
                        return Mono.error(new DomainException(DomainError.CAPACIDAD_EXISTE));
                    }
                    return capacidadPersistencePort.guardar(capacidad);
                })
                .then();
    }

    //HU3
    @Override
    public Flux<Capacidad> listarCapacidades(int page, int size, String sortField, String direction) {
        return capacidadPersistencePort.listarCapacidades(page, size, sortField, direction);
    }
}
