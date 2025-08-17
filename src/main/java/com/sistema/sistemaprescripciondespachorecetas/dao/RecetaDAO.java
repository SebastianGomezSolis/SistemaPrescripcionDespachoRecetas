package com.sistema.sistemaprescripciondespachorecetas.dao;

import com.sistema.sistemaprescripciondespachorecetas.data.DataReceta;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;

public class RecetaDAO {
    private static final String file = "recetas.xml";

    public void guardar(DataReceta data) {
        XMLHelper.getInstancia().guardarReceta(data, file);
    }

    public DataReceta cargar() {
        DataReceta data = XMLHelper.getInstancia().cargarReceta(file);
        return data != null ? data : new DataReceta();
    }

}
