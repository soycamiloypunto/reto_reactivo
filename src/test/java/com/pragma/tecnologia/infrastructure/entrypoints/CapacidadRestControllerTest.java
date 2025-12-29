package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.ICapacidadServicePort;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadResponse;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.ICapacidadMapper; // Verifica si tu mapper se llama así o ICapacidadRequestMapper
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = CapacidadRestController.class)
class CapacidadRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ICapacidadServicePort capacidadServicePort;

    @MockBean
    private ICapacidadMapper capacidadMapper; // Ojo: Asegúrate de que el nombre coincida con tu interfaz real

    private List<Tecnologia> tecnologiasValidas;

    @BeforeEach
    void setUp() {
        // Creamos una lista que cumpla la regla de negocio (Mínimo 3)
        tecnologiasValidas = Arrays.asList(
                new Tecnologia(1L, "Java", "Desc"),
                new Tecnologia(2L, "Python", "Desc"),
                new Tecnologia(3L, "Go", "Desc")
        );
    }

    @Test
    void registrarCapacidad_DeberiaRetornar201() {
        // ARRANGE
        // El Request puede ir vacío si no tienes @Valid estricto, pero el Dominio NO.
        CapacidadRequest request = new CapacidadRequest(1L, "Java", "Desc", Collections.emptyList());

        // Aquí es donde fallaba: Debemos retornar una Capacidad VÁLIDA (con 3 tecnologías)
        Capacidad capacidadValida = new Capacidad(1L, "Java", "Desc", tecnologiasValidas);

        when(capacidadMapper.toDomain(any())).thenReturn(capacidadValida);
        when(capacidadServicePort.registrarCapacidad(any())).thenReturn(Mono.empty());

        // ACT & ASSERT
        webTestClient.post()
                .uri("/capacidad")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void listarCapacidades_DeberiaRetornar200() {
        // 1. ARRANGE
        // Preparar datos para el objeto de Dominio (Input del Mapper)
        Capacidad capacidad = new Capacidad(1L, "Java", "Desc", tecnologiasValidas);

        // Preparar datos para el Record de Respuesta (Output del Mapper)
        // Usamos el CONSTRUCTOR porque los records no tienen setters
        List<CapacidadResponse.TecnologiaSimplificada> tecsSimples = List.of(
                new CapacidadResponse.TecnologiaSimplificada(1L, "Java")
        );
        CapacidadResponse responseDto = new CapacidadResponse(1L, "Java", "Desc", tecsSimples);

        // 2. MOCKS
        when(capacidadServicePort.listarCapacidades(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(Flux.just(capacidad));

        // Simulamos la conversión
        when(capacidadMapper.toResponse(any(Capacidad.class))).thenReturn(responseDto);

        // 3. ACT & ASSERT
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/capacidad")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CapacidadResponse.class)
                .hasSize(1);
    }
}