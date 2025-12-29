package com.pragma.tecnologia.domain.model;

import com.pragma.tecnologia.domain.exceptions.DomainException;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CapacidadTest {

    @Test
    void crearCapacidad_ConDatosValidos_DeberiaSerExitoso() {
        // ARRANGE
        List<Tecnologia> tecnologias = Arrays.asList(
                new Tecnologia(1L, "Java", "Desc"),
                new Tecnologia(2L, "Spring", "Desc"),
                new Tecnologia(3L, "MySQL", "Desc")
        );

        // ACT
        Capacidad capacidad = new Capacidad(1L, "Backend Java", "Descripcion", tecnologias);

        // ASSERT
        assertNotNull(capacidad);
        assertEquals(3, capacidad.getTecnologias().size());
    }

    @Test
    void crearCapacidad_ConMenosDeTresTecnologias_DeberiaLanzarExcepcion() {
        // ARRANGE: Solo 2 tecnologías
        List<Tecnologia> tecnologias = Arrays.asList(
                new Tecnologia(1L, "Java", "Desc"),
                new Tecnologia(2L, "Spring", "Desc")
        );

        // ACT & ASSERT
        assertThrows(DomainException.class, () ->
                new Capacidad(1L, "Nombre", "Desc", tecnologias));
    }

    @Test
    void crearCapacidad_ConTecnologiasRepetidas_DeberiaLanzarExcepcion() {
        // ARRANGE: IDs repetidos
        List<Tecnologia> tecnologias = Arrays.asList(
                new Tecnologia(1L, "Java", "Desc"),
                new Tecnologia(1L, "Java", "Desc"),
                new Tecnologia(2L, "Spring", "Desc")
        );

        // ACT & ASSERT
        assertThrows(DomainException.class, () ->
                new Capacidad(1L, "Nombre", "Desc", tecnologias));
    }
}