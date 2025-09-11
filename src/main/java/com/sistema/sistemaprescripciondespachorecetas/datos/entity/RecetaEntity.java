package com.sistema.sistemaprescripciondespachorecetas.datos.entity;

import com.sistema.sistemaprescripciondespachorecetas.datos.LocalDateAdapter;
import com.sistema.sistemaprescripciondespachorecetas.model.Medicamento;
import com.sistema.sistemaprescripciondespachorecetas.model.Paciente;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javafx.fxml.FXML;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class RecetaEntity {
    private String id;
    private PacienteEntity paciente;
    private MedicamentoEntity medicamento;
    private String estado;
    private int cantidad;
    private String indicaciones;
    private int diasDuracion;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaEntrega;

    public RecetaEntity() {}

    public String getId() { return id; }
    public PacienteEntity getPaciente() { return paciente; }
    public MedicamentoEntity getMedicamento() { return medicamento; }
    public int getCantidad() { return cantidad; }
    public String getIndicaciones() { return indicaciones; }
    public int getDiasDuracion() { return diasDuracion; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public String getEstado() { return estado; }

    public void setId(String id) { this.id = id; }
    public void setPaciente(PacienteEntity paciente) { this.paciente = paciente; }
    public void setMedicamento(MedicamentoEntity medicamento) { this.medicamento = medicamento; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public void setDiasDuracion(int diasDuracion) { this.diasDuracion = diasDuracion; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public void setEstado(String estado) { this.estado = estado; }

}
