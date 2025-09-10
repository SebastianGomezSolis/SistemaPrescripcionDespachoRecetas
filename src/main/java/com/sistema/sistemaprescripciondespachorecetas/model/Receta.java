package com.sistema.sistemaprescripciondespachorecetas.model;

import java.time.LocalDate;

public class Receta {
    private String id;                           // Id unico consecutivo de la receta
    private Paciente paciente;                   // Paciente que se le atribuye la receta
    private Medicamento medicamento;             // Medicamento que se receta
    private LocalDate fechaEntrega;
    private String estado;                       // Estado de la receta
    private int cantidad;                        // Cantidad en mg, g, ml, etc.
    private String indicaciones;                 // Indicaciones adicionales para el medicamento
    private int diasDuracion;                    // Dias de duracion de la receta

    // Constructor sin parametros
    public Receta() {}

    // Constructor con parametros
    public Receta(String id, Paciente paciente, Medicamento medicamento, LocalDate fechaEntrega, int cantidad, String indicaciones, int diasDuracion) {
        this.id = id;
        this.paciente = paciente;
        this.medicamento = medicamento;
        this.fechaEntrega = fechaEntrega;
        this.estado = "Confeccionada";
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.diasDuracion = diasDuracion;
    }

    // Getters y Setters
    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medicamento getMedicamento() { return medicamento; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public String getEstado() { return estado; }
    public String getIndicaciones() { return indicaciones; }
    public int getDiasDuracion() { return diasDuracion; }
    public int getCantidad() { return cantidad; }

    public void setId(String id) { this.id = id; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public void setDiasDuracion(int diasDuracion) { this.diasDuracion = diasDuracion; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

}
