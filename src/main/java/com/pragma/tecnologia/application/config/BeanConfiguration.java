package com.pragma.tecnologia.application.config;

import com.pragma.tecnologia.domain.api.IBootcampServicePort;
import com.pragma.tecnologia.domain.api.ICapacidadServicePort;
import com.pragma.tecnologia.domain.api.IMatriculaServicePort;
import com.pragma.tecnologia.domain.api.ITecnologiaServicePort;
import com.pragma.tecnologia.domain.spi.*;
import com.pragma.tecnologia.domain.usecase.BootcampUseCase;
import com.pragma.tecnologia.domain.usecase.CapacidadUseCase;
import com.pragma.tecnologia.domain.usecase.MatriculaUseCase;
import com.pragma.tecnologia.domain.usecase.TecnologiaUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public ITecnologiaServicePort tecnologiaServicePort(ITecnologiaPersistencePort tecnologiaPersistencePort) {
        return new TecnologiaUseCase(tecnologiaPersistencePort);
    }

    @Bean
    public ICapacidadServicePort capacidadServicePort(ICapacidadPersistencePort capacidadPersistencePort) {
        return new CapacidadUseCase(capacidadPersistencePort);
    }

    @Bean
    public IMatriculaServicePort matriculaServicePort(
            IMatriculaPersistencePort matriculaPersistencePort,
            IBootcampPersistencePort bootcampPersistencePort,
            IReportePersistencePort reportePersistencePort // Inyectamos también el puerto de bootcamp
    ) {
        return new MatriculaUseCase(matriculaPersistencePort, bootcampPersistencePort, reportePersistencePort);
    }

    @Bean
    public IBootcampServicePort bootcampServicePort(
            IBootcampPersistencePort bootcampPersistencePort,
            IReportePersistencePort reportePersistencePort,
            IMatriculaPersistencePort matriculaPersistencePort,
            IUsuarioGatewayPort usuarioGatewayPort
    ) {
        return new BootcampUseCase(
                bootcampPersistencePort,
                reportePersistencePort,
                matriculaPersistencePort,
                usuarioGatewayPort
        );
    }

}
