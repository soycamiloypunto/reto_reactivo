package com.pragma.tecnologia.infrastructure.entrypoints;

import com.pragma.tecnologia.domain.api.IBootcampServicePort;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampRequest;
import com.pragma.tecnologia.infrastructure.entrypoints.mapper.IBootcampMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bootcamp")
@RequiredArgsConstructor
public class BootcampRestController {
    private final IBootcampServicePort bootcampServicePort;
    private final IBootcampMapper bootcampMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> registrar(@RequestBody BootcampRequest request) {
        return bootcampServicePort.guardarBootcamp(bootcampMapper.toDomain(request));
    }
}