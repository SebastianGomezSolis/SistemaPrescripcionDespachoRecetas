package com.sistema.sistemaprescripciondespachorecetas.datos.entity;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javafx.fxml.FXML;

@XmlAccessorType(XmlAccessType.FIELD)
public class FarmaceutaEntity {
    private String id;
    private String clave;
    private String nombre;

    public FarmaceutaEntity() {}

    // Getters y Setters
    public String getId() { return id; }
    public String getClave() { return clave; }
    public String getNombre() { return nombre; }

    public void setId(String id) { this.id = id; }
    public void setClave(String clave) { this.clave = clave; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
