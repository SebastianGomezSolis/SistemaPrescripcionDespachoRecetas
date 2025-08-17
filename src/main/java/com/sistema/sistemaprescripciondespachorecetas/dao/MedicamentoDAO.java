package com.sistema.sistemaprescripciondespachorecetas.dao;

import com.sistema.sistemaprescripciondespachorecetas.data.DataMedicamento;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;

public class MedicamentoDAO {
    private static final String file = "medicamentos.xml";

    public void guardar(DataMedicamento data) {
        XMLHelper.getInstancia().guardarMedicamento(data, file);
    }

    public DataMedicamento cargar() {
        DataMedicamento data = XMLHelper.getInstancia().cargarMedicamento(file);
        return data != null ? data : new DataMedicamento();
    }

}
