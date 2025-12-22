package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.ICapacidadServicePort;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.CapacidadResponse;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.ICapacidadMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public Mono<ResponseEntity<Void>> registrar(@Valid @RequestBody CapacidadRequest request) {
        // ESTA LÍNEA ES PARA DEBUG: Mira la consola de IntelliJ al hacer el POST
        System.out.println("LOG: Recibido nombre -> " + request.getNombre());

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