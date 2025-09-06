package com.sistema.sistemaprescripciondespachorecetas.datos.entity;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javafx.fxml.FXML;

@XmlAccessorType(XmlAccessType.FIELD)
public class MedicoEntity {
    private String id;
    private String clave;
    private String nombre;
    private String especialidad;

    public MedicoEntity() {}

    // Getters y Setters
    public String getId() { return id; }
    public String getClave() { return clave; }
    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }

    public void setId(String id) { this.id = id; }
    public void setClave(String clave) { this.clave = clave; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

}
