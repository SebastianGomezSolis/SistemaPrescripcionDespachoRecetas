package com.sistema.sistemaprescripciondespachorecetas.datos.entity;

import com.sistema.sistemaprescripciondespachorecetas.datos.LocalDateAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javafx.fxml.FXML;

import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class PacienteEntity {
    private String id;
    private String nombre;
    private String telefono;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaNacimiento;

    public PacienteEntity() {}

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getTelefono() { return telefono; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

}
