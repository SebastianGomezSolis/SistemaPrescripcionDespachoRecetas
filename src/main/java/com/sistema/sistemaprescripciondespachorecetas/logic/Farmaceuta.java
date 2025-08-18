package com.sistema.sistemaprescripciondespachorecetas.logic;

public class Farmaceuta {
    private String id;
    private String clave;
    private String nombre;

    // Constructor sin parametros
    public Farmaceuta() {}

    // Constructor con parametros
    public Farmaceuta(String id, String clave, String nombre) {
        this.id = id;
        this.clave = clave;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getClave() { return clave; }
    public String getNombre() { return nombre; }

    public void setId(String id) { this.id = id; }
    public void setClave(String clave) { this.clave = clave; }
    public void setNombre(String nombre) { this.nombre = nombre; }

}
