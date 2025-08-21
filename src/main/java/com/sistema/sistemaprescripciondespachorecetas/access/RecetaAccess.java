package com.sistema.sistemaprescripciondespachorecetas.access;

import com.sistema.sistemaprescripciondespachorecetas.data.DataReceta;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;
import com.sistema.sistemaprescripciondespachorecetas.logic.Receta;

public class RecetaAccess {
    private static final String file = "recetas.xml";

    public void guardar(DataReceta data) {
        XMLHelper.getInstancia().guardarReceta(data, file);
    }

    public DataReceta cargar() {
        DataReceta data = XMLHelper.getInstancia().cargarReceta(file);
        return data != null ? data : new DataReceta();
    }

    public void agregarReceta(Receta receta) {
        DataReceta data = cargar();
        data.agregarReceta(receta);
        guardar(data);
    }

    public Receta buscarRecetaPorId(String id) {
        return cargar().buscarReceta(id);
    }

    public boolean actualizarReceta(Receta recetaModificada) {
        DataReceta data = cargar();
        if (data.modificarReceta(recetaModificada)) {
            guardar(data);
            return true;
        }
        return false;
    }

    public boolean actualizarEstado(String id, String nuevoEstado) {
        DataReceta data = cargar();
        if (data.actualizarEstado(id, nuevoEstado)) {
            guardar(data);
            return true;
        }
        return false;
    }

    public void eliminarReceta(String id) {
        DataReceta data = cargar();
        data.eliminarReceta(id);
        guardar(data);
    }

    public DataReceta listarRecetas() {
        return cargar();
    }

}
