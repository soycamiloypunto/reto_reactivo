package com.pragma.tecnologia.infrastructure.entrypoints.dto;

public class MatriculaRequest {
    private Long idUsuario;
    private Long idBootcamp;

    // Getters y Setters
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public Long getIdBootcamp() { return idBootcamp; }
    public void setIdBootcamp(Long idBootcamp) { this.idBootcamp = idBootcamp; }
}