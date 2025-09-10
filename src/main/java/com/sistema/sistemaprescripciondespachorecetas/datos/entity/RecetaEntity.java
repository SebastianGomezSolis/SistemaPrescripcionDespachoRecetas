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
    private Paciente paciente;
    private Medicamento medicamento;
    private int cantidad;
    private String indicaciones;
    private int diasDuracion;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaEntrega;

    public RecetaEntity() {}

    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medicamento getMedicamento() { return medicamento; }
    public int getCantidad() { return cantidad; }
    public String getIndicaciones() { return indicaciones; }
    public int getDiasDuracion() { return diasDuracion; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }

    public void setId(String id) { this.id = id; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public void setDiasDuracion(int diasDuracion) { this.diasDuracion = diasDuracion; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

}
