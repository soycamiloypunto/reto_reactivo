package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.IBootcampServicePort;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort;
import com.pragma.tecnologia.domain.spi.IMatriculaPersistencePort;
import com.pragma.tecnologia.domain.spi.IReportePersistencePort;
import com.pragma.tecnologia.domain.spi.IUsuarioGatewayPort;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampExitosoResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BootcampUseCase implements IBootcampServicePort {
    private final IBootcampPersistencePort bootcampPersistencePort;
    private final IReportePersistencePort reportePersistencePort;
    private final IMatriculaPersistencePort matriculaPersistencePort;
    private final IUsuarioGatewayPort usuarioGatewayPort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort,
                           IReportePersistencePort reportePersistencePort,
                           IMatriculaPersistencePort matriculaPersistencePort,
                           IUsuarioGatewayPort usuarioGatewayPort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
        this.reportePersistencePort = reportePersistencePort;
        this.matriculaPersistencePort = matriculaPersistencePort;
        this.usuarioGatewayPort = usuarioGatewayPort;
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

    //HU8
    @Override
    public Mono<BootcampExitosoResponse> obtenerBootcampMasExitoso() {
        return matriculaPersistencePort.obtenerBootcampIdMasInscritos()
                .flatMap(bootcampId ->
                        // 1. Buscamos la info completa del Bootcamp (reusando listarBootcamps o similar lógica)
                        // Nota: Asumo que en el persistence port puedes buscar uno solo completo, si no, lo creamos abajo.
                        bootcampPersistencePort.obtenerBootcampCompleto(bootcampId)
                                .zipWith(
                                        // 2. Buscamos los usuarios y convertimos sus IDs a Nombres
                                        matriculaPersistencePort.obtenerIdsUsuariosInscritos(bootcampId)
                                                .flatMap(usuarioGatewayPort::obtenerUsuarioPorId)
                                                .collectList()
                                )
                )
                .map(tuple -> new BootcampExitosoResponse(
                        tuple.getT1().getNombre(),       // Nombre Bootcamp
                        tuple.getT1().getCapacidades(),  // Malla (Caps + Tecnologías)
                        tuple.getT2()                    // Lista de Estudiantes con nombre
                ));
    }
}
