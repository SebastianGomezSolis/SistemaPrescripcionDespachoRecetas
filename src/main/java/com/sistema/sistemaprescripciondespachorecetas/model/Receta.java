package com.sistema.sistemaprescripciondespachorecetas.model;

import java.time.LocalDate;

public class Receta {
    private String id;
    private Paciente paciente;
    private Medicamento medicamento;
    private int cantidad;
    private String indicaciones;
    private int duracion;
    private LocalDate fechaEntrega;
    private String estado;

    // Constructor sin parametros
    public Receta() {}

    // Constructor con parametros
    public Receta(String id,Paciente paciente, Medicamento medicamento, int cantidad, String indicaciones, int duracion, LocalDate fechaEntrega, String estado) {
        this.id = id;
        this.paciente = paciente;
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracion = duracion;
        this.fechaEntrega = fechaEntrega;
        this.estado = estado;
    }

    // Getters y Setters
    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medicamento getMedicamento() { return medicamento; }
    public int getCantidad() { return cantidad; }
    public String getIndicaciones() { return indicaciones; }
    public int getDuracion() { return duracion; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public String getEstado() { return estado; }

    public void setId(String id) { this.id = id; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public void setEstado(String estado) { this.estado = estado; }

}
