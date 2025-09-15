package com.sistema.sistemaprescripciondespachorecetas.datos.entity;

import com.sistema.sistemaprescripciondespachorecetas.datos.LocalDateAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "receta")
public class RecetaEntity {
    private String id;
    private PacienteEntity paciente;
    private String estado;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaEntrega;

    @XmlElementWrapper(name = "medicamentos")
    @XmlElement(name = "medicamentoDetalle")
    private List<RecetaDetalleEntity> medicamentos = new ArrayList<>();

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public PacienteEntity getPaciente() { return paciente; }
    public void setPaciente(PacienteEntity paciente) { this.paciente = paciente; }

    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<RecetaDetalleEntity> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<RecetaDetalleEntity> medicamentos) { this.medicamentos = medicamentos; }
}
