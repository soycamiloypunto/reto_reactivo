package com.pragma.tecnologia.domain.util;

import com.pragma.tecnologia.domain.exceptions.DomainError;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Bootcamp;
import com.pragma.tecnologia.domain.model.Capacidad;

import java.time.LocalDate;
import java.util.List;

public class BootcampValidator {

    public static void validar(Bootcamp bootcamp) {
        // Validar campos obligatorios básicos (Nombre, Descripción, Duración, etc.)
        if (bootcamp.getNombre() == null || bootcamp.getNombre().isEmpty()) {
            throw new DomainException(DomainError.NOMBRE_OBLIGATORIO);
        }

        // REGLA 1: Cantidad de Capacidades (1 a 4)
        List<Capacidad> caps = bootcamp.getCapacidades();
        if (caps == null || caps.isEmpty() || caps.size() > 4) {
            throw new DomainException(DomainError.BOOTCAMP_CAPACIDADES_RANGO);
        }

        // REGLA 2: Fecha futura (Solo en creación -> ID null)
        if (bootcamp.getId() == null) {
            LocalDate fecha = bootcamp.getFechaLanzamiento();
            if (fecha == null || fecha.isBefore(LocalDate.now())) {
                throw new DomainException(DomainError.FECHA_LANZAMIENTO_PASADA);
            }
        }
    }
}
