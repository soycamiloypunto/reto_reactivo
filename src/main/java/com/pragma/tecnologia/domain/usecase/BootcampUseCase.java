package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.IBootcampServicePort;
import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort;
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
}
