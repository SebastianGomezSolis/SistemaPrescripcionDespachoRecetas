package com.sistema.sistemaprescripciondespachorecetas.model;

import java.time.LocalDate;

public class Receta {
    private String id;                           // Id unico consecutivo de la receta
    private Paciente paciente;                   // Paciente que se le atribuye la receta
    private Medico medicoAsociado;               // Medico que realiza la receta
    private Medicamento medicamento;             // Medicamento que se receta
    private LocalDate fechaConfeccion;
    private LocalDate fechaEntrega;
    private String estado;
    private DetalleReceta detalleReceta;

    // Constructor sin parametros
    public Receta() {}

    // Constructor con parametros
    public Receta(String id, Paciente paciente, Medico medicoAsociado, Medicamento medicamento, LocalDate fechaConfeccion, DetalleReceta detalleReceta) {
        this.id = id;
        this.paciente = paciente;
        this.medicoAsociado = medicoAsociado;
        this.medicamento = medicamento;
        this.fechaConfeccion = fechaConfeccion;
        this.fechaEntrega = this.fechaConfeccion;
        this.estado = "Confeccionada";
        this.detalleReceta = detalleReceta;
    }

    // Getters y Setters
    public String getId() { return id; }
    public Paciente getPaciente() { return paciente; }
    public Medico getMedicoAsociado() { return medicoAsociado; }
    public Medicamento getMedicamento() { return medicamento; }
    public LocalDate getFechaConfeccion() { return fechaConfeccion; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public String getEstado() { return estado; }
    public DetalleReceta getDetalleReceta() { return detalleReceta; }

    public void setId(String id) { this.id = id; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setMedicoAsociado(Medico medicoAsociado) { this.medicoAsociado = medicoAsociado; }
    public void setMedicamento(Medicamento medicamento) { this.medicamento = medicamento; }
    public void setFechaConfeccion(LocalDate fechaConfeccion) { this.fechaConfeccion = fechaConfeccion; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setDetalleReceta(DetalleReceta detalleReceta) { this.detalleReceta = detalleReceta; }

}
