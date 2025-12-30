package com.pragma.tecnologia.infrastructure.output.r2dbc.adapter;

import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.spi.IReportePersistencePort;
import com.pragma.tecnologia.infrastructure.output.r2dbc.entity.ReporteBootcampEntity;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.ICapacidadTecnologiaRepository;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.IReporteBootcampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ReportePersistenceAdapter implements IReportePersistencePort {

    private final IReporteBootcampRepository reporteRepository;
    private final ICapacidadTecnologiaRepository capacidadTecnologiaRepository; // Inyectar esto

    @Override
    public Mono<Void> guardarReporte(Bootcamp bootcamp) {
        // 1. Calcular Tecnologías Reales (Consulta Reactiva)
        return Flux.fromIterable(bootcamp.getCapacidades()) // Recorremos IDs (1, 3)
                .flatMap(cap -> capacidadTecnologiaRepository.countByCapacidadId(cap.getId())) // Contamos sus tecnologías
                .reduce(0L, Long::sum) // Sumamos los totales (ej. 3 + 4 = 7)
                .flatMap(totalTecnologias -> {

                    // 2. Crear entidad con el total calculado
                    ReporteBootcampEntity reporte = new ReporteBootcampEntity(
                            null,
                            bootcamp.getId(),
                            bootcamp.getNombre(),
                            bootcamp.getCapacidades().size(),
                            totalTecnologias.intValue(), // Valor real
                            0,
                            LocalDate.now()
                    );

                    // 3. Guardar
                    return reporteRepository.save(reporte);
                })
                .then();
    }

    @Override
    public Mono<Void> incrementarInscritos(Long bootcampId) {
        return reporteRepository.incrementarInscritos(bootcampId).then();
    }
}