package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.ICapacidadServicePort;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.spi.ICapacidadPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CapacidadUseCase implements ICapacidadServicePort {

    private final ICapacidadPersistencePort capacidadPersistencePort;

    public CapacidadUseCase(ICapacidadPersistencePort capacidadPersistencePort) {
        this.capacidadPersistencePort = capacidadPersistencePort;
    }

    @Override
    public Mono<Void> registrarCapacidad(Capacidad capacidad) {
        return capacidadPersistencePort.existePorNombre(capacidad.getNombre())
                .flatMap(existe -> {
                    if (Boolean.TRUE.equals(existe)) {
                        return Mono.error(new DomainException("La capacidad ya existe"));
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
