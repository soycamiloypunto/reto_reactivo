package com.pragma.tecnologia.domain.spi;

import com.pragma.tecnologia.domain.model.Bootcamp;
import reactor.core.publisher.Mono;

public interface IReportePersistencePort {
    // Recibe el bootcamp completo para extraer las métricas
    Mono<Void> guardarReporte(Bootcamp bootcamp);
}