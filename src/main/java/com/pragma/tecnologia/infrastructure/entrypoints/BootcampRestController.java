package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.IBootcampServicePort;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampResponse;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.IBootcampMapper;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.IBootcampResponseMapper;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
}