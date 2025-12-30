package com.pragma.tecnologia.domain.exceptions;

public enum DomainError {
    // Generales
    NOMBRE_OBLIGATORIO("El nombre es obligatorio"),
    DESCRIPCION_OBLIGATORIA("La descripción es obligatoria"),

    // Tecnologia
    NOMBRE_LARGO("El nombre no puede exceder los 50 caracteres"),
    DESCRIPCION_LARGO("La descripción no puede exceder los 90 caracteres"),

    TECNOLOGIA_YA_EXISTE("La tecnología ya existe"),

    //Matricula
    MATRICULA_EXISTENTE("Esta matrícula ya existe en las fechas seleccionadas"),

    // Capacidad
    CAPACIDAD_TECNOLOGIAS_MINIMO("La capacidad debe tener entre 3 y 20 tecnologías"),
    CAPACIDAD_DUPLICADA("No se permiten tecnologías repetidas en la capacidad"),
    CAPACIDAD_EXISTE("La capacidad ya existe"),

    // Bootcamp
    BOOTCAMP_CAPACIDADES_RANGO("Un bootcamp debe tener entre 1 y 4 capacidades"),
    FECHA_LANZAMIENTO_PASADA("La fecha de lanzamiento debe ser futura"),
    PAGINACION_INVALIDA("Parámetros de paginación inválidos");



    private final String message;

    DomainError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}