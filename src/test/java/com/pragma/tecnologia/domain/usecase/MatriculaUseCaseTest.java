package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Matricula;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort;
import com.pragma.tecnologia.domain.spi.IMatriculaPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatriculaUseCaseTest {

    @Mock
    private IMatriculaPersistencePort matriculaPersistencePort;

    @Mock
    private IBootcampPersistencePort bootcampPersistencePort;

    @InjectMocks
    private MatriculaUseCase matriculaUseCase;

    @Test
    void inscribirUsuario_CuandoBootcampExiste_DeberiaGuardarExitosamente() {
        // ARRANGE
        Matricula matricula = new Matricula(100L, 1L, 1L);
        // Simulamos que el bootcamp con ID 1 sí existe
        when(bootcampPersistencePort.obtenerBootcampPorId(1L))
                .thenReturn(Mono.just(new Bootcamp(1L, "Java", "Desc", LocalDate.now().plusDays(10), 12, Collections.emptyList())));
        when(matriculaPersistencePort.guardarMatricula(any(Matricula.class))).thenReturn(Mono.empty());

        // ACT & ASSERT
        StepVerifier.create(matriculaUseCase.inscribirUsuario(matricula))
                .verifyComplete();

        verify(bootcampPersistencePort).obtenerBootcampPorId(1L);
        verify(matriculaPersistencePort).guardarMatricula(any(Matricula.class));
    }

    @Test
    void inscribirUsuario_CuandoBootcampNoExiste_DeberiaLanzarExcepcion() {
        // ARRANGE
        Matricula matricula = new Matricula(100L, 999L, 999L);
        // Simulamos que el bootcamp NO existe devolviendo Mono.empty()
        when(bootcampPersistencePort.obtenerBootcampPorId(999L)).thenReturn(Mono.empty());

        // ACT & ASSERT
        StepVerifier.create(matriculaUseCase.inscribirUsuario(matricula))
                .expectError(DomainException.class)
                .verify();

        // Verificamos que NUNCA se llamó al puerto de guardar matrícula
        verify(matriculaPersistencePort, never()).guardarMatricula(any());
    }
}