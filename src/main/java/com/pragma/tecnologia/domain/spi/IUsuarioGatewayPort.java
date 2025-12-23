package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampExitosoResponse.EstudianteInfo;
import reactor.core.publisher.Mono;

public interface IUsuarioGatewayPort {
    Mono<EstudianteInfo> obtenerUsuarioPorId(Long id);
}