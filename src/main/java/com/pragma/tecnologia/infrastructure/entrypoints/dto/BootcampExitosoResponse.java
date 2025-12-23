package com.pragma.tecnologia.infrastructure.entrypoints.dto;

import com.pragma.tecnologia.domain.model.Capacidad;
import java.util.List;

public class BootcampExitosoResponse {
    private String nombreBootcamp;
    private List<Capacidad> mallaCurricular; // Capacidades y Tecnologías
    private List<EstudianteInfo> estudiantesInscritos;

    public BootcampExitosoResponse(String nombreBootcamp, List<Capacidad> mallaCurricular, List<EstudianteInfo> estudiantesInscritos) {
        this.nombreBootcamp = nombreBootcamp;
        this.mallaCurricular = mallaCurricular;
        this.estudiantesInscritos = estudiantesInscritos;
    }

    // Getters y Setters
    public String getNombreBootcamp() { return nombreBootcamp; }
    public List<Capacidad> getMallaCurricular() { return mallaCurricular; }
    public List<EstudianteInfo> getEstudiantesInscritos() { return estudiantesInscritos; }

    // Clase interna o separada para el estudiante
    public static class EstudianteInfo {
        private String nombre;
        private String correo;

        public EstudianteInfo(String nombre, String correo) {
            this.nombre = nombre;
            this.correo = correo;
        }
        // Getters
        public String getNombre() { return nombre; }
        public String getCorreo() { return correo; }
    }
}