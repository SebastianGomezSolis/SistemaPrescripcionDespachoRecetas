package com.sistema.sistemaprescripciondespachorecetas.data;

import com.sistema.sistemaprescripciondespachorecetas.logic.Administrador;

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

    public boolean modificarAdministrador(Administrador administradorModificado) {
        for (int i = 0; i < administradores.size(); i++) {
            if (administradores.get(i).getId().equals(administradorModificado.getId())) {
                administradores.set(i, administradorModificado);
                return true;
            }
        }
        return false;
    }

    public boolean verificarClave(String id, String clave) {
        for (Administrador administrador : administradores) {
            if (administrador.getId().equals(id) && administrador.getClave().equals(clave)) {
                return true;
            }
        }
        return false;
    }

    public boolean actualizarClave(String id, String claveActual, String nuevaClave) {
        for (Administrador administrador : administradores) {
            if (administrador.getId().equals(id)) {
                if (verificarClave(id, claveActual)) {
                    administrador.setClave(nuevaClave);
                    return true;
                }
            }
        }
        return false;
    }


    public List<Administrador> getAdministradores() { return administradores; }

}
