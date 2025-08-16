package com.sistema.sistemaprescripciondespachorecetas.model;

public class Administrador {
    private String id;
    private String clave;

    // Constructor sin parametros
    public Administrador() {}

    // Constructor con parametros
    public Administrador(String id, String clave) {
        this.id = id;
        this.clave = clave;
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getClave() { return clave; }

    public void setId(String id) { this.id = id; }
    public void setClave(String clave) { this.clave = clave; }

}
