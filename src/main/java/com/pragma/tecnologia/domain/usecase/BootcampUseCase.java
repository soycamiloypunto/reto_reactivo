package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.IBootcampServicePort;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort;
import com.pragma.tecnologia.domain.spi.IReportePersistencePort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BootcampUseCase implements IBootcampServicePort {
    private final IBootcampPersistencePort bootcampPersistencePort;
    private final IReportePersistencePort reportePersistencePort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort, IReportePersistencePort reportePersistencePort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
        this.reportePersistencePort = reportePersistencePort;
    }

    @Override
    public Mono<Void> guardarBootcamp(Bootcamp bootcamp) {
        return bootcampPersistencePort.guardarBootcamp(bootcamp)
                .doOnSuccess(bootcampGuardado -> {
                    // FIRE AND FORGET: Disparamos el reporte sin bloquear el retorno
                    reportePersistencePort.guardarReporte(bootcampGuardado)
                            .subscribeOn(reactor.core.scheduler.Schedulers.boundedElastic())
                            .subscribe(); // <-- Esto ejecuta el reporte "aparte"
                })
                .then(); // Retornamos Void al controlador inmediatamente
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
