package com.sistema.sistemaprescripciondespachorecetas.dao;

import com.sistema.sistemaprescripciondespachorecetas.data.DataAdministrador;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;

public class AdministradorDAO {
    private static final String file = "administradores.xml";

    public void guardar(DataAdministrador data) {
        XMLHelper.getInstancia().guardarAdministrador(data, file);
    }

    public DataAdministrador cargar() {
        DataAdministrador data = XMLHelper.getInstancia().cargarAdministrador(file);
        return data != null ? data : new DataAdministrador();
    }

}
