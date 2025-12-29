package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.IMatriculaServicePort;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.MatriculaRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.IMatriculaRequestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/matricula")
@RequiredArgsConstructor
public class MatriculaRestController {

    private final IMatriculaServicePort matriculaServicePort;
    private final IMatriculaRequestMapper matriculaRequestMapper;

    @Operation(summary = "Registrar una nueva Matrícula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Matriculad@ exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o usuario/bootcamp no existen", content = @Content), // Descripción más precisa
            @ApiResponse(responseCode = "404", description = "Bootcamp no encontrado", content = @Content) // Agregado por lógica HU7
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> inscribirUsuario(@RequestBody MatriculaRequest request) {
        return matriculaServicePort.inscribirUsuario(matriculaRequestMapper.toDomain(request));
    }
}