package com.pragma.tecnologia.domain.util;

import com.pragma.tecnologia.domain.exceptions.DomainError;
import com.pragma.tecnologia.domain.exceptions.DomainException;
import com.pragma.tecnologia.domain.model.Tecnologia;

public class TecnologiaValidator {
    public static void validar(Tecnologia tecnologia) {
        if (tecnologia.getNombre() == null || tecnologia.getNombre().trim().isEmpty()) {
            throw new DomainException(DomainError.NOMBRE_OBLIGATORIO);
        }
        if (tecnologia.getNombre().length() > DomainConstants.MAX_NOMBRE_LENGTH) {
            throw new DomainException(DomainError.NOMBRE_LARGO);
        }
        if (tecnologia.getDescripcion() == null || tecnologia.getDescripcion().trim().isEmpty()) {
            throw new DomainException(DomainError.DESCRIPCION_OBLIGATORIA);
        }
        if (tecnologia.getDescripcion().length() > DomainConstants.MAX_DESCRIPCION_LENGTH) {
            throw new DomainException(DomainError.DESCRIPCION_LARGO);
        }
    }
}