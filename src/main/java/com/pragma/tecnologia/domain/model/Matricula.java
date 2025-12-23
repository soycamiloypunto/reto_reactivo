package com.pragma.tecnologia.domain.model;

public class Matricula {
    private final Long id;
    private final Long idUsuario;
    private final Long idBootcamp;

    public Matricula(Long id, Long idUsuario, Long idBootcamp) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idBootcamp = idBootcamp;
    }

    public Long getId() { return id; }
    public Long getIdUsuario() { return idUsuario; }
    public Long getIdBootcamp() { return idBootcamp; }
}