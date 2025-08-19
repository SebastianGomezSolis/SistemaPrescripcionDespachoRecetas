package com.sistema.sistemaprescripciondespachorecetas.logic;

public class Administrador extends Usuario {

    // Constructor con parametros
    public Administrador(String id, String clave) {
        super(id, clave);
    }

    // Getters y Setters
    public String getId() { return id; }
    public String getClave() { return clave; }

    public void setId(String id) { this.id = id; }
    public void setClave(String clave) { this.clave = clave; }

}
