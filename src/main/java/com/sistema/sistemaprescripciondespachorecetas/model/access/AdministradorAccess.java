package com.sistema.sistemaprescripciondespachorecetas.model.access;

import com.sistema.sistemaprescripciondespachorecetas.model.data.DataAdministrador;
import com.sistema.sistemaprescripciondespachorecetas.model.data.XMLHelper;
import com.sistema.sistemaprescripciondespachorecetas.model.logic.Administrador;

public class AdministradorAccess {
    private static final String file = "administradores.xml";

    public void guardar(DataAdministrador data) {
        XMLHelper.getInstancia().guardarAdministrador(data, file);
    }

    public DataAdministrador cargar() {
        DataAdministrador data = XMLHelper.getInstancia().cargarAdministrador(file);
        return data != null ? data : new DataAdministrador();
    }

    public void agregarAdministrador(Administrador admin) {
        DataAdministrador data = cargar();
        data.agregarAdministrador(admin);
        guardar(data);
    }

    public Administrador buscarPorId(String id) {
        return cargar().buscarAdministrador(id);
    }

    public boolean actualizarAdministrador(Administrador administradorModificado) {
        DataAdministrador data = cargar();
        if (data.modificarAdministrador(administradorModificado)) {
            guardar(data);
            return true;
        }
        return false;
    }

    public boolean actualizarClave(String id, String claveActual, String nuevaClave) {
        DataAdministrador data = cargar();
        if (data.actualizarClave(id, claveActual, nuevaClave)) {
            guardar(data);
            return true;
        }
        return false;
    }

    public void eliminarAdministrador(String id) {
        DataAdministrador data = cargar();
        data.eliminarAdministrador(id);
        guardar(data);
    }

    public DataAdministrador listarAdministradores() {
        return cargar();
    }

}
