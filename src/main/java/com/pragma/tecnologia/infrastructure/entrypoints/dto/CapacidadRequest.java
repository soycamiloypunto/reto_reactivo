package com.pragma.tecnologia.infrastructure.entrypoints.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
public class CapacidadRequest {
    private String nombre;
    private String descripcion;
    private List<Long> tecnologiasIds;

    public CapacidadRequest() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Long> getTecnologiasIds() { return tecnologiasIds; }
    public void setTecnologiasIds(List<Long> tecnologiasIds) { this.tecnologiasIds = tecnologiasIds; }
}