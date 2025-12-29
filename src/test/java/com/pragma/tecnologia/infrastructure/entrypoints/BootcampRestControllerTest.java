package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.IBootcampServicePort;
import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampResponse;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.IBootcampMapper;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.IBootcampResponseMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = BootcampRestController.class)
class BootcampRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private IBootcampServicePort bootcampServicePort;

    @MockBean
    private IBootcampResponseMapper bootcampResponseMapper; // Mapper de Respuesta

    @MockBean
    private IBootcampMapper bootcampRequestMapper;   // Mapper de Request (Necesario para POST)

    private List<Capacidad> capacidadesValidas;

    @BeforeEach
    void setUp() {
        // Preparamos datos que pasen las validaciones de dominio (Min 3 tecs, Min 1 Cap)
        List<Tecnologia> tecs = Arrays.asList(
                new Tecnologia(1L, "Java", "Desc"),
                new Tecnologia(2L, "Spring", "Desc"),
                new Tecnologia(3L, "Docker", "Desc")
        );
        capacidadesValidas = Collections.singletonList(
                new Capacidad(1L, "Fullstack", "Desc", tecs)
        );
    }

    @Test
    void registrarBootcamp_DeberiaRetornar201() {
        // 1. ARRANGE
        // DTO Request
        CapacidadRequest capReq = new CapacidadRequest(); capReq.setId(1L);
        BootcampRequest request = new BootcampRequest();
        request.setNombre("Java");
        request.setDescripcion("Desc");
        request.setFechaLanzamiento(LocalDate.now().plusDays(10));
        request.setDuracion(12);
        request.setCapacidades(List.of(capReq));

        // Dominio Válido (Usamos la lista creada en setUp)
        Bootcamp bootcampDominio = new Bootcamp(1L, "Java", "Desc", LocalDate.now().plusDays(10), 12, capacidadesValidas);

        // 2. MOCKS
        // Simulamos conversión Request -> Dominio
        when(bootcampRequestMapper.toDomain(any(BootcampRequest.class))).thenReturn(bootcampDominio);
        // Simulamos guardado
        when(bootcampServicePort.guardarBootcamp(any(Bootcamp.class))).thenReturn(Mono.empty());

        // 3. ACT & ASSERT
        webTestClient.post()
                .uri("/bootcamp")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void obtenerMasInscritos_DeberiaRetornar200() {

        // DTO de respuesta
        com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampExitosoResponse responseDto =
                new com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampExitosoResponse("Python", Collections.emptyList(), Collections.emptyList());

        when(bootcampServicePort.obtenerBootcampMasExitoso()).thenReturn(Mono.just(responseDto));

        webTestClient.get()
                .uri("/bootcamp/mas-inscritos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                // CAMBIO AQUÍ: Usa el nombre real del atributo en tu DTO
                .jsonPath("$.nombreBootcamp").isEqualTo("Python");
    }

    @Test
    void listarBootcamps_DeberiaRetornar200() {
        // ARRANGE
        Bootcamp bootcamp = new Bootcamp(1L, "Java", "Desc", LocalDate.now().plusDays(10), 12, capacidadesValidas);
        // Respuesta DTO (Record o Clase)
        BootcampResponse responseDto = new BootcampResponse(1L, "Java", "Desc", Collections.emptyList());

        // MOCKS
        when(bootcampServicePort.listarBootcamps(anyInt(), anyInt(), anyString(), anyString()))
                .thenReturn(Flux.just(bootcamp));

        // Mock del Mapper Response (Importante para que el controller devuelva algo)
        when(bootcampResponseMapper.toResponse(any(Bootcamp.class))).thenReturn(responseDto);

        // ACT & ASSERT
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/bootcamp")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BootcampResponse.class)
                .hasSize(1);
    }
}