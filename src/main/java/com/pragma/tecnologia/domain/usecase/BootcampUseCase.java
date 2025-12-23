package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.IBootcampServicePort;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BootcampUseCase implements IBootcampServicePort {
    private final IBootcampPersistencePort bootcampPersistencePort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
    }

    @Override
    public Mono<Void> guardarBootcamp(Bootcamp bootcamp) {
        return bootcampPersistencePort.guardarBootcamp(bootcamp);
    }

    @Override
    public Flux<Bootcamp> listarBootcamps(int page, int size, String sortField, String direction) {
        // Validaciones de negocio si fueran necesarias
        if (page < 0 || size <= 0) {
            throw new DomainException("Parámetros de paginación inválidos");
        }
        return bootcampPersistencePort.listarBootcamps(page, size, sortField, direction);
    }

    //HU6
    @Override
    public Mono<Void> eliminarBootcamp(Long bootcampId) {
        return bootcampPersistencePort.eliminarBootcamp(bootcampId);
    }
}
