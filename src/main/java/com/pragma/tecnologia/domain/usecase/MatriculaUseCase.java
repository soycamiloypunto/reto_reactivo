package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.IMatriculaServicePort;
import com.pragma.tecnologia.domain.exceptions.DomainError;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Matricula;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort; // Necesitamos esto para buscar el nuevo bootcamp
import com.pragma.tecnologia.domain.spi.IMatriculaPersistencePort;
import com.pragma.tecnologia.domain.spi.IReportePersistencePort;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;

public class MatriculaUseCase implements IMatriculaServicePort {

    private final IMatriculaPersistencePort matriculaPersistencePort;
    private final IBootcampPersistencePort bootcampPersistencePort; // Para obtener datos del bootcamp nuevo
    private final IReportePersistencePort reportePersistencePort;

    // CONSTRUCTOR: ¡Aquí faltaba agregar el reporte!
    public MatriculaUseCase(IMatriculaPersistencePort matriculaPersistencePort,
                            IBootcampPersistencePort bootcampPersistencePort,
                            IReportePersistencePort reportePersistencePort) {
        this.matriculaPersistencePort = matriculaPersistencePort;
        this.bootcampPersistencePort = bootcampPersistencePort;
        this.reportePersistencePort = reportePersistencePort; // Inicialización obligatoria
    }

    @Override
    public Mono<Void> inscribirUsuario(Matricula matricula) {
        // 1. Obtener detalles del bootcamp
        return bootcampPersistencePort.obtenerBootcampPorId(matricula.getIdBootcamp())
                .switchIfEmpty(Mono.error(new DomainException("El bootcamp no existe")))
                .flatMap(nuevoBootcamp ->
                        // 2. Validar cantidad de inscripciones (Max 5)
                        matriculaPersistencePort.contarMatriculasUsuario(matricula.getIdUsuario())
                                .flatMap(count -> {
                                    if (count >= 5) {
                                        return Mono.error(new DomainException("El usuario ya tiene 5 matriculas activas"));
                                    }

                                    // 3. Validar cruce de fechas y guardar
                                    return validarCruceDeFechas(matricula.getIdUsuario(), nuevoBootcamp)
                                            // Guardamos la matrícula
                                            .then(matriculaPersistencePort.guardarMatricula(matricula))
                                            // 4. ACTUALIZAR REPORTE
                                            // Usamos 'then' para asegurar que se ejecute después del guardado,
                                            // independientemente de si 'guardarMatricula' devuelve Void o un Objeto.
                                            .then(Mono.defer(() ->
                                                    reportePersistencePort.incrementarInscritos(matricula.getIdBootcamp())
                                                            .subscribeOn(Schedulers.boundedElastic()) // Async para no bloquear
                                            ))
                                            .then(); // Retorno final Void
                                })
                );
    }

    private Mono<Void> validarCruceDeFechas(Long idUsuario, Bootcamp nuevoBootcamp) {
        LocalDate inicioNuevo = nuevoBootcamp.getFechaLanzamiento();
        LocalDate finNuevo = inicioNuevo.plusWeeks(nuevoBootcamp.getDuracion());

        return matriculaPersistencePort.obtenerBootcampsInscritos(idUsuario)
                .filter(bootcampInscrito -> {
                    LocalDate inicioInscrito = bootcampInscrito.getFechaLanzamiento();
                    LocalDate finInscrito = inicioInscrito.plusWeeks(bootcampInscrito.getDuracion());

                    // Lógica de cruce de fechas
                    return inicioNuevo.isBefore(finInscrito) && finNuevo.isAfter(inicioInscrito);
                })
                .hasElements()
                .flatMap(seCruza -> {
                    if (Boolean.TRUE.equals(seCruza)) {
                        // Aquí usé tu Enum DomainError, asegúrate que MATRICULA_EXISTENTE exista
                        return Mono.error(new DomainException(DomainError.MATRICULA_EXISTENTE));
                    }
                    return Mono.empty();
                });
    }

}