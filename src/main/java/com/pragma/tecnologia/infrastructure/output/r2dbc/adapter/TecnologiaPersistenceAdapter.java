package com.pragma.tecnologia.infrastructure.output.r2dbc.adapter;

import com.pragma.tecnologia.domain.model.Tecnologia;
import com.pragma.tecnologia.domain.spi.ITecnologiaPersistencePort;
import com.pragma.tecnologia.infrastructure.output.r2dbc.repository.ITecnologiaRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TecnologiaPersistenceAdapter implements ITecnologiaPersistencePort {

    private final ITecnologiaRepository repository;

    public TecnologiaPersistenceAdapter(ITecnologiaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Tecnologia> guardar(Tecnologia tecnologia) {
        // Mapeo de Modelo -> Entidad
        Tecnologia entity = new Tecnologia(null, tecnologia.getNombre(), tecnologia.getDescripcion());
        return repository.save(entity)
                .map(saved -> new Tecnologia(saved.getId(), saved.getNombre(), saved.getDescripcion()));
    }

    @Override
    public Mono<Boolean> existePorNombre(String nombre) {
        return repository.existsByNombre(nombre);
    }
}
