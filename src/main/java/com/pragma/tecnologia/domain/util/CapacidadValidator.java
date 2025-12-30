package com.pragma.tecnologia.domain.util;

import com.pragma.tecnologia.domain.exceptions.DomainError;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Capacidad;
import com.pragma.tecnologia.domain.model.Tecnologia;

import java.util.List;

public class CapacidadValidator {

    public static void validar(Capacidad capacidad) {
        if (capacidad.getNombre() == null || capacidad.getNombre().length() > 50) {
            throw new DomainException(DomainError.NOMBRE_LARGO);
        }
        if (capacidad.getDescripcion() == null || capacidad.getDescripcion().length() > 90) {
            throw new DomainException(DomainError.DESCRIPCION_LARGO);
        }

        List<Tecnologia> tecs = capacidad.getTecnologias();
        if (tecs == null || tecs.size() < 3 || tecs.size() > 20) {
            throw new DomainException(DomainError.CAPACIDAD_TECNOLOGIAS_MINIMO);
        }

        if (tieneTecnologiasRepetidas(tecs)) {
            throw new DomainException(DomainError.CAPACIDAD_DUPLICADA);
        }
    }

    private static boolean tieneTecnologiasRepetidas(List<Tecnologia> lista) {
        return lista.stream()
                .map(Tecnologia::getId)
                .distinct()
                .count() != lista.size();
    }
}