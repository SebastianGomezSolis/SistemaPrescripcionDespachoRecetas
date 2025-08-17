package com.sistema.sistemaprescripciondespachorecetas.data;

import com.sistema.sistemaprescripciondespachorecetas.model.Administrador;

import java.util.ArrayList;
import java.util.List;

public class DataAdministrador {
    private List<Administrador> administradores;

    public DataAdministrador() { administradores = new ArrayList<>(); }

    public void agregarAdministrador(Administrador administrador) { administradores.add(administrador); }

    public void eliminarAdministrador(String id) { administradores.removeIf(administrador -> administrador.getId().equals(id)); }

    public Administrador buscarAdministrador(String id) {
        for (Administrador administrador : administradores) {
            if (administrador.getId().equals(id)) {
                return administrador;
            }
        }
        return null;
    }

    public void modificarAdministrador(Administrador administradorModificado) {
        for (int i = 0; i < administradores.size(); i++) {
            if (administradores.get(i).getId().equals(administradorModificado.getId())) {
                administradores.set(i, administradorModificado);
                return;
            }
        }
    }

    public List<Administrador> getAdministradores() { return administradores; }

}
