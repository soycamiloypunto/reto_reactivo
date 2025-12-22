package com.pragma.tecnologia.domain.exceptions;

public class DomainException extends RuntimeException {
    public DomainException(String mensaje) {
        super(mensaje);
    }
}