package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.api.IMatriculaServicePort;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Matricula;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort; // Necesitamos esto para buscar el nuevo bootcamp
import com.pragma.tecnologia.domain.spi.IMatriculaPersistencePort;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public class MatriculaUseCase implements IMatriculaServicePort {

    private final IMatriculaPersistencePort matriculaPersistencePort;
    private final IBootcampPersistencePort bootcampPersistencePort; // Para obtener datos del bootcamp nuevo

    public MatriculaUseCase(IMatriculaPersistencePort matriculaPersistencePort, IBootcampPersistencePort bootcampPersistencePort) {
        this.matriculaPersistencePort = matriculaPersistencePort;
        this.bootcampPersistencePort = bootcampPersistencePort;
    }

    @Override
    public Mono<Void> inscribirUsuario(Matricula matricula) {
        // 1. Obtener detalles del bootcamp al que se quiere inscribir (Fechas)
        return bootcampPersistencePort.obtenerBootcampPorId(matricula.getIdBootcamp())
                .switchIfEmpty(Mono.error(new DomainException("El bootcamp no existe")))
                .flatMap(nuevoBootcamp ->
                        // 2. Validar cantidad de inscripciones actuales
                        matriculaPersistencePort.contarMatriculasUsuario(matricula.getIdUsuario())
                                .flatMap(count -> {
                                    if (count >= 5) {
                                        return Mono.error(new DomainException("El usuario ya tiene 5 matriculas activas"));
                                    }
                                    // 3. Validar cruce de fechas con los otros bootcamps
                                    return validarCruceDeFechas(matricula.getIdUsuario(), nuevoBootcamp)
                                            .then(matriculaPersistencePort.guardarMatricula(matricula));
                                })
                );
    }

    private Mono<Void> validarCruceDeFechas(Long idUsuario, Bootcamp nuevoBootcamp) {
        // Calculamos fecha fin del nuevo bootcamp (Inicio + Duración en Semanas)
        LocalDate inicioNuevo = nuevoBootcamp.getFechaLanzamiento();
        LocalDate finNuevo = inicioNuevo.plusWeeks(nuevoBootcamp.getDuracion());

        return matriculaPersistencePort.obtenerBootcampsInscritos(idUsuario)
                .filter(bootcampInscrito -> {
                    LocalDate inicioInscrito = bootcampInscrito.getFechaLanzamiento();
                    LocalDate finInscrito = inicioInscrito.plusWeeks(bootcampInscrito.getDuracion());

                    // Algoritmo de Solapamiento: (InicioA < FinB) && (FinA > InicioB)
                    return inicioNuevo.isBefore(finInscrito) && finNuevo.isAfter(inicioInscrito);
                })
                .hasElements() // ¿Encontró algún bootcamp que se cruce?
                .flatMap(seCruza -> {
                    if (seCruza) {
                        return Mono.error(new DomainException("Las fechas del bootcamp se cruzan con uno ya inscrito"));
                    }
                    return Mono.empty();
                });
    }
}