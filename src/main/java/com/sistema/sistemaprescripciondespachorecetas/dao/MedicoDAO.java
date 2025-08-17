package com.sistema.sistemaprescripciondespachorecetas.dao;

import com.sistema.sistemaprescripciondespachorecetas.data.DataMedico;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;

public class MedicoDAO {
    private static final String file = "medicos.xml";

    public void guardar(DataMedico data) {
        XMLHelper.getInstancia().guardarMedico(data, file);
    }

    public DataMedico cargar() {
        DataMedico data = XMLHelper.getInstancia().cargarMedico(file);
        return data != null ? data : new DataMedico();
    }

}
