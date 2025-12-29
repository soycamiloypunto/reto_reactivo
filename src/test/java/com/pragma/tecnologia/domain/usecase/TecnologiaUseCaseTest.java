package com.pragma.tecnologia.domain.usecase;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.ITecnologiaPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Habilita Mockito
class TecnologiaUseCaseTest {

    @Mock // 1. Creamos el puerto falso (Simulacro)
    ITecnologiaPersistencePort persistencePort;

    @InjectMocks // 2. Inyectamos el simulacro en el Caso de Uso real
    TecnologiaUseCase useCase;

    @Test
    void registrarTecnologia_SiNoExiste_DeberiaGuardar() {
        // ARRANGE (Preparar el escenario)
        Tecnologia tecnologia = new Tecnologia(null, "Java", "Backend");

        // Enseñamos al Mock: "Cuando te pregunten si existe 'Java', di que NO (false)"
        when(persistencePort.existePorNombre("Java")).thenReturn(Mono.just(false));

        // Enseñamos al Mock: "Cuando te manden guardar, di que todo salió bien (empty)"
        when(persistencePort.guardar(any(Tecnologia.class))).thenReturn(Mono.empty());

        // ACT & ASSERT (Ejecutar y Verificar con Reactor)
        StepVerifier.create(useCase.registrarTecnologia(tecnologia))
                .verifyComplete(); // Esperamos que termine exitosamente (200 OK)

        // Verificamos que el método guardar SÍ fue llamado
        verify(persistencePort).guardar(any(Tecnologia.class));
    }

    @Test
    void registrarTecnologia_SiYaExiste_DeberiaLanzarError() {
        // ARRANGE
        Tecnologia tecnologia = new Tecnologia(null, "Java", "Backend");

        // Enseñamos al Mock: "Cuando te pregunten, di que SÍ existe (true)"
        when(persistencePort.existePorNombre("Java")).thenReturn(Mono.just(true));

        // ACT & ASSERT
        StepVerifier.create(useCase.registrarTecnologia(tecnologia))
                .expectErrorMessage("La tecnología ya existe") // Esperamos el error
                .verify();
    }
}