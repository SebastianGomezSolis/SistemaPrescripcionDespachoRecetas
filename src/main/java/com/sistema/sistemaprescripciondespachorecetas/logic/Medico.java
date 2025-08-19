package com.sistema.sistemaprescripciondespachorecetas.logic;

public class Medico extends Usuario {
    private String nombre;
    private String especialidad;

    // Constructor con parametros
    public Medico(String id, String clave, String nombre, String especialidad) {
        super(id, clave);
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

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
