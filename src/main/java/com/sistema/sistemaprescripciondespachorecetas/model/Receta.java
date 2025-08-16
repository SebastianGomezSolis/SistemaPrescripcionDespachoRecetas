package com.sistema.sistemaprescripciondespachorecetas.model;

import java.time.LocalDate;

public class Receta {
    Paciente paciente;
    Medicamento medicamento;
    int cantidad;
    String indicaciones;
    int duracion;
    LocalDate fechaEntrega;

    // Constructor sin parametros
    public Receta() {}

    // Constructor con parametros
    public Receta(Paciente paciente, Medicamento medicamento, int cantidad, String indicaciones, int duracion, LocalDate fechaEntrega) {
        this.paciente = paciente;
        this.medicamento = medicamento;
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.duracion = duracion;
        this.fechaEntrega = fechaEntrega;
    }

    // Getters y Setters
    public Paciente getPaciente() { return paciente; }
    public Medicamento getMedicamento() { return medicamento; }
    public int getCantidad() { return cantidad; }
    public String getIndicaciones() { return indicaciones; }
    public int getDuracion() { return duracion; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }

    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }

}
