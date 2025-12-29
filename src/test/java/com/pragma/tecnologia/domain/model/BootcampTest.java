package com.pragma.tecnologia.domain.model;

import com.pragma.tecnologia.domain.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BootcampTest {

    private List<Tecnologia> tecnologiasValidas;

    @BeforeEach
    void setUp() {
        // 1. Preparamos tecnologías para que la Capacidad no se queje
        tecnologiasValidas = Arrays.asList(
                new Tecnologia(1L, "Java", "Desc"),
                new Tecnologia(2L, "Python", "Desc"),
                new Tecnologia(3L, "Go", "Desc")
        );
    }

    @Test
    void crearBootcamp_ConDatosValidos_DeberiaSerExitoso() {
        // ARRANGE
        // Creamos una Capacidad VÁLIDA (con sus 3 tecnologías)
        Capacidad capacidadValida = new Capacidad(1L, "Backend", "Desc", tecnologiasValidas);
        List<Capacidad> listaCapacidades = Arrays.asList(capacidadValida);

        LocalDate fechaFutura = LocalDate.now().plusDays(30);

        // ACT
        Bootcamp bootcamp = new Bootcamp(1L, "Fullstack", "Desc", fechaFutura, 12, listaCapacidades);

        // ASSERT
        assertNotNull(bootcamp);
        assertEquals("Fullstack", bootcamp.getNombre());
    }

    @Test
    void crearBootcamp_ConFechaPasada_DeberiaLanzarExcepcion() {
        // ARRANGE
        LocalDate fechaPasada = LocalDate.now().minusDays(1);

        // Preparamos capacidad válida para que no falle por eso
        Capacidad capacidadValida = new Capacidad(1L, "Backend", "Desc", tecnologiasValidas);
        List<Capacidad> listaCapacidades = Collections.singletonList(capacidadValida);

        // ACT & ASSERT
        // CAMBIO IMPORTANTE: Pasamos null en el ID para activar tu validación
        assertThrows(DomainException.class, () ->
                new Bootcamp(null, "Nombre", "Desc", fechaPasada, 12, listaCapacidades));
    }

    @Test
    void crearBootcamp_SinCapacidades_DeberiaLanzarExcepcion() {
        // ARRANGE: Lista vacía de CAPACIDADES (esto es lo que queremos probar del Bootcamp)
        List<Capacidad> listaVacia = Collections.emptyList();

        // ACT & ASSERT
        assertThrows(DomainException.class, () ->
                new Bootcamp(1L, "Nombre", "Desc", LocalDate.now().plusDays(1), 12, listaVacia));
    }
}