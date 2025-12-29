package com.pragma.tecnologia.domain.model;

import com.pragma.tecnologia.domain.exceptions.DomainException; // Asegúrate de importar tu excepción
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TecnologiaTest {

    @Test
    void crearTecnologia_ConDatosValidos_DeberiaSerExitoso() {
        // Arrange (Preparar)
        String nombre = "Java";
        String descripcion = "Lenguaje de programación";

        // Act (Actuar)
        Tecnologia tecnologia = new Tecnologia(1L, nombre, descripcion);

        // Assert (Verificar)
        assertNotNull(tecnologia);
        assertEquals("Java", tecnologia.getNombre());
    }

    @Test
    void crearTecnologia_ConNombreVacio_DeberiaLanzarExcepcion() {
        // Arrange
        String nombreVacio = "";

        // Act & Assert (Verificar que explote)
        assertThrows(DomainException.class, () -> {
            new Tecnologia(null, nombreVacio, "Descripcion valida");
        });
    }
}