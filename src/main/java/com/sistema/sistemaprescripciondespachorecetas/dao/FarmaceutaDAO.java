package com.sistema.sistemaprescripciondespachorecetas.dao;

import com.sistema.sistemaprescripciondespachorecetas.data.DataFarmauceuta;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;

public class FarmaceutaDAO {
    private static final String file = "farmaceutas.xml";

    public void guardar(DataFarmauceuta data) {
        XMLHelper.getInstancia().guardarFarmauceuta(data, file);
    }

    public DataFarmauceuta cargar() {
        DataFarmauceuta data = XMLHelper.getInstancia().cargarFarmauceuta(file);
        return data != null ? data : new DataFarmauceuta();
    }

}
