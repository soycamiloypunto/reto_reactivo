package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.ICapacidadServicePort;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadResponse;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.ICapacidadMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/capacidad")
public class CapacidadRestController {

    private final ICapacidadServicePort iCapacidadServicePort;
    private final ICapacidadMapper capacidadMapper;

    public CapacidadRestController(ICapacidadServicePort iCapacidadServicePort, ICapacidadMapper capacidadMapper) {
        this.iCapacidadServicePort = iCapacidadServicePort;
        this.capacidadMapper = capacidadMapper;
    }

    //Hu2
    @Operation(summary = "Registrar una nueva Capacidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Capacidad creada exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos (<3 tecnologías) o repetidos", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Void>> registrar(@Valid @RequestBody CapacidadRequest request) {
        return iCapacidadServicePort.registrarCapacidad(capacidadMapper.toDomain(request))
                .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @GetMapping
    public Flux<CapacidadResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortField,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return iCapacidadServicePort.listarCapacidades(page, size, sortField, direction)
                .map(capacidadMapper::toResponse);
    }
}