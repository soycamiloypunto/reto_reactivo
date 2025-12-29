package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.ICapacidadPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CapacidadUseCaseTest {

    @Mock
    private ICapacidadPersistencePort persistencePort;

    @InjectMocks
    private CapacidadUseCase useCase;

    private List<Tecnologia> tecnologiasValidas;

    @BeforeEach
    void setUp() {
        // Preparamos datos que SÍ pasen la validación del constructor (mínimo 3)
        tecnologiasValidas = Arrays.asList(
                new Tecnologia(1L, "Java", "Desc"),
                new Tecnologia(2L, "Python", "Desc"),
                new Tecnologia(3L, "Go", "Desc")
        );
    }

    @Test
    void registrarCapacidad_CuandoNoExiste_DeberiaGuardar() {
        // ARRANGE: Usamos la lista válida
        Capacidad capacidad = new Capacidad(null, "Java Backend", "Desc", tecnologiasValidas);

        when(persistencePort.existePorNombre(anyString())).thenReturn(Mono.just(false));
        when(persistencePort.guardar(any(Capacidad.class))).thenReturn(Mono.empty());

        // ACT & ASSERT
        StepVerifier.create(useCase.registrarCapacidad(capacidad))
                .verifyComplete();

        verify(persistencePort).guardar(any(Capacidad.class));
    }

    @Test
    void listarCapacidades_DeberiaRetornarFlujo() {
        // ARRANGE: Usamos la lista válida
        Capacidad cap = new Capacidad(1L, "Java", "Desc", tecnologiasValidas);

        when(persistencePort.listarCapacidades(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(Flux.just(cap));

        // ACT & ASSERT
        StepVerifier.create(useCase.listarCapacidades(0, 10, "nombre", "ASC"))
                .expectNext(cap)
                .verifyComplete();
    }
}