package com.pragma.tecnologia.domain.exceptions;

public class DomainException extends RuntimeException {

    // Constructor que acepta el Enum (La forma recomendada)
    public DomainException(DomainError error) {
        super(error.getMessage());
    }

    // (Opcional) Mantener el de String por si necesitas mensajes dinámicos muy específicos
    public DomainException(String message) {
        super(message);
    }
}