package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.IMatriculaServicePort;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.MatriculaRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.IMatriculaRequestMapper;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> inscribirUsuario(@RequestBody MatriculaRequest request) {
        return matriculaServicePort.inscribirUsuario(matriculaRequestMapper.toDomain(request));
    }
}