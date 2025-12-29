package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.ITecnologiaServicePort;
import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.ITecnologiaRequestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = TecnologiaRestController.class) // Solo carga la capa web
class TecnologiaRestControllerTest {

    @Autowired
    private WebTestClient webTestClient; // Simula el cliente HTTP (Insomnia)

    @MockBean
    private ITecnologiaServicePort tecnologiaServicePort; // Simula el UseCase

    @MockBean
    private ITecnologiaRequestMapper tecnologiaRequestMapper; // Simula el Mapper

    @Test
    void registrarTecnologia_DeberiaRetornar201() {
        // ARRANGE
        TecnologiaRequest request = new TecnologiaRequest();
        request.setNombre("JAVA TEST");
        request.setDescripcion("Descripcion Java Test");

        when(tecnologiaRequestMapper.toDomain(any())).thenReturn(new Tecnologia(null, "Java", "Backend"));
        when(tecnologiaServicePort.registrarTecnologia(any())).thenReturn(Mono.empty());

        // ACT & ASSERT
        webTestClient.post()
                .uri("/tecnologia")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated(); // Verifica que devuelva 201
    }
}