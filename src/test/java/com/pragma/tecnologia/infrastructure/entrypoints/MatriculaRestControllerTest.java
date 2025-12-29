package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.IMatriculaServicePort;
import com.pragma.tecnologia.domain.model.Matricula;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.MatriculaRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.IMatriculaRequestMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = MatriculaRestController.class)
class MatriculaRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private IMatriculaServicePort matriculaServicePort;

    @MockBean
    private IMatriculaRequestMapper matriculaRequestMapper;

    @Test
    void inscribirUsuario_DeberiaRetornar201() {
        // ARRANGE
        MatriculaRequest request = new MatriculaRequest();
        request.setIdUsuario(100L);
        request.setIdBootcamp(1L);

        Matricula matriculaDominio = new Matricula(100L, 1L, 1L);

        when(matriculaRequestMapper.toDomain(any())).thenReturn(matriculaDominio);
        when(matriculaServicePort.inscribirUsuario(any())).thenReturn(Mono.empty());

        // ACT & ASSERT
        webTestClient.post()
                .uri("/matricula")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated(); // Verifica el 201
    }
}