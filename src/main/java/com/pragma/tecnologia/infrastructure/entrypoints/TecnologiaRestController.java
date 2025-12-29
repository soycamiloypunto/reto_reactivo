package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.ITecnologiaServicePort;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.TecnologiaRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.ITecnologiaRequestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tecnologia")
public class TecnologiaRestController {

    private final ITecnologiaServicePort tecnologiaServicePort;
    private final ITecnologiaRequestMapper tecnologiaRequestMapper;

    public TecnologiaRestController(ITecnologiaServicePort tecnologiaServicePort,
                                    ITecnologiaRequestMapper tecnologiaRequestMapper) {
        this.tecnologiaServicePort = tecnologiaServicePort;
        this.tecnologiaRequestMapper = tecnologiaRequestMapper;
    }

    //HU1
    @Operation(summary = "Registrar un nueva tecnologia") // Corregido género
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "tecnologia creada exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o nombre repetido", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> registrar(@RequestBody TecnologiaRequest tecnologiaRequest) {
        return tecnologiaServicePort.registrarTecnologia(
                tecnologiaRequestMapper.toDomain(tecnologiaRequest)
        );
    }
}