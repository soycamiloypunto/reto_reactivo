package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.IBootcampServicePort;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampExitosoResponse;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampResponse;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.IBootcampMapper;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.IBootcampResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bootcamp")
@RequiredArgsConstructor
public class BootcampRestController {
    private final IBootcampServicePort bootcampServicePort;
    private final IBootcampMapper bootcampMapper;
    private final IBootcampResponseMapper iBootcampResponseMapper;

    //HU4
    @Operation(summary = "Registrar un nuevo Bootcamp")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bootcamp creado exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o nombre repetido", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //
    public Mono<Void> registrar(@RequestBody BootcampRequest request) {
        return bootcampServicePort.guardarBootcamp(bootcampMapper.toDomain(request));
    }

    @GetMapping
    public Flux<BootcampResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortField, // 'nombre' o 'capacidades'
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return bootcampServicePort.listarBootcamps(page, size, sortField, direction)
                .map(iBootcampResponseMapper::toResponse);
    }

    //HU6
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminar(@PathVariable Long id) {
        return bootcampServicePort.eliminarBootcamp(id);
    }

    //HU9
    @GetMapping("/mas-inscritos")
    public Mono<BootcampExitosoResponse> obtenerBootcampMasInscritos() {
        return bootcampServicePort.obtenerBootcampMasExitoso();
    }
}