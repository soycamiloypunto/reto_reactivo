package com.pragma.tecnologia.domain.util;

public final class DomainConstants {
    // Evitamos instanciar la clase
    private DomainConstants() {}

    // REGLAS GENERALES
    public static final int MAX_NOMBRE_LENGTH = 50;
    public static final int MAX_DESCRIPCION_LENGTH = 90;

    // REGLAS DE CAPACIDAD
    public static final int MIN_TECNOLOGIAS_POR_CAPACIDAD = 3;
    public static final int MAX_TECNOLOGIAS_POR_CAPACIDAD = 20;

    // REGLAS DE BOOTCAMP
    public static final int MIN_CAPACIDADES_POR_BOOTCAMP = 1;
    public static final int MAX_CAPACIDADES_POR_BOOTCAMP = 4;
    public static final int MAX_MATRICULAS_POR_USUARIO = 5;
}