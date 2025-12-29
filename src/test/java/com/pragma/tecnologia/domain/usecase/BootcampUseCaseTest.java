package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.IBootcampPersistencePort;
import com.pragma.tecnologia.domain.spi.IReportePersistencePort; // 1. IMPORTANTE: Importa tu interfaz de reporte
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BootcampUseCaseTest {

    @Mock
    private IBootcampPersistencePort persistencePort;

    @Mock
    private IReportePersistencePort reportePersistencePort; // 2. AGREGADO: Mock del reporte

    @InjectMocks
    private BootcampUseCase useCase;

    private List<Capacidad> capacidadesValidas;

    @BeforeEach
    void setUp() {
        // Preparar datos válidos para evitar excepciones de dominio
        List<Tecnologia> tecs = Arrays.asList(
                new Tecnologia(1L, "Java", "Desc"),
                new Tecnologia(2L, "Spring", "Desc"),
                new Tecnologia(3L, "Docker", "Desc")
        );
        Capacidad cap = new Capacidad(1L, "Fullstack", "Desc", tecs);
        capacidadesValidas = Arrays.asList(cap);
    }

    @Test
    void guardarBootcamp_DeberiaLlamarPersistencia() {
        // ARRANGE
        Bootcamp bootcamp = new Bootcamp(null, "Java", "Desc", LocalDate.now().plusDays(10), 12, capacidadesValidas);

        // Comportamiento del puerto de Bootcamp
        when(persistencePort.guardarBootcamp(any(Bootcamp.class))).thenReturn(Mono.empty());

        // 3. AGREGADO: Comportamiento del puerto de Reporte (debe devolver empty para seguir el flujo)
        // Nota: Si tu método guardarReporte devuelve void o Mono, ajústalo aquí. Asumo Mono<Void>
        when(reportePersistencePort.guardarReporte(any())).thenReturn(Mono.empty());

        // ACT & ASSERT
        StepVerifier.create(useCase.guardarBootcamp(bootcamp))
                .verifyComplete();

        verify(reportePersistencePort).guardarReporte(any());
    }

    @Test
    void listarBootcamps_DeberiaRetornarFlujoPaginado() {
        // ARRANGE
        Bootcamp b = new Bootcamp(1L, "Java", "Desc", LocalDate.now().plusDays(1), 5, capacidadesValidas);

        when(persistencePort.listarBootcamps(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(Flux.just(b));

        // ACT & ASSERT
        StepVerifier.create(useCase.listarBootcamps(0, 10, "nombre", "ASC"))
                .expectNext(b)
                .verifyComplete();
    }
}