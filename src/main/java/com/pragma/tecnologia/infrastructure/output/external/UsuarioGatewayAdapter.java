package com.pragma.tecnologia.infrastructure.output.external;

import com.pragma.tecnologia.domain.spi.IUsuarioGatewayPort;
import com.pragma.tecnologia.infrastructure.entrypoints.dto.BootcampExitosoResponse.EstudianteInfo;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UsuarioGatewayAdapter implements IUsuarioGatewayPort {
    @Override
    public Mono<EstudianteInfo> obtenerUsuarioPorId(Long id) {
        // SIMULACIÓN: En producción esto sería una llamada HTTP WebClient
        return Mono.just(new EstudianteInfo(
                "Estudiante Simulado " + id,
                "estudiante" + id + "@correo.com"
        ));
    }
}