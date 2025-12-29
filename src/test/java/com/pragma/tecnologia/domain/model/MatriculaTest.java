package com.pragma.tecnologia.domain.model;

import com.pragma.tecnologia.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatriculaTest {

    @Test
    void crearMatricula_ConDatosValidos_DeberiaSerExitoso() {
        // Arrange & Act
        Matricula matricula = new Matricula(100L, 1L, 1L);

        // Assert
        assertEquals(100L, matricula.getIdUsuario());
        assertEquals(1L, matricula.getIdBootcamp());
    }

    @Test
    void crearMatricula_ConIdUsuarioNulo_DeberiaLanzarExcepcion() {
        // Act & Assert
        assertThrows(DomainException.class, () ->
                new Matricula(null, null, 1L));
    }

    @Test
    void crearMatricula_ConIdBootcampNulo_DeberiaLanzarExcepcion() {
        // Act & Assert
        assertThrows(DomainException.class, () ->
                new Matricula(100L, null, null));
    }
}