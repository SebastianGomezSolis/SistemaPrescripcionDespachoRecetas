package com.sistema.sistemaprescripciondespachorecetas.model;

import com.sistema.sistemaprescripciondespachorecetas.data.*;

public class XMLHelper {
    private static XMLHelper instancia;

    private XMLHelper() {}

    public static XMLHelper getInstancia() {
        if (instancia == null) {
            instancia = new XMLHelper();
        }
        return instancia;
    }

    public void guardarAdministrador(DataAdministrador data, String path) {}

    public void guardarFarmauceuta(DataFarmauceuta data, String path) {}

    public void guardarMedicamento(DataMedicamento data, String path) {}

    public void guardarMedico(DataMedico data, String path) {}

    public void guardarPaciente(DataPaciente data, String path) {}

    public void guardarReceta(DataReceta data, String path) {}

    public DataAdministrador cargarAdministrador(String path) {
        return null;
    }

    public DataFarmauceuta cargarFarmauceuta(String path) {
        return null;
    }

    public DataReceta cargarReceta(String path) {
        return null;
    }

    public DataPaciente cargarPaciente(String path) {
        return null;
    }

    public DataMedicamento cargarMedicamento(String path) {
        return null;
    }

    public DataMedico cargarMedico(String path) {
        return null;
    }

}
