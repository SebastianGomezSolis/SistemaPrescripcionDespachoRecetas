package com.sistema.sistemaprescripciondespachorecetas.dao;

import com.sistema.sistemaprescripciondespachorecetas.data.DataPaciente;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;

public class PacienteDAO {
    private static final String file = "pacientes.xml";

    public void guardar(DataPaciente data) {
        XMLHelper.getInstancia().guardarPaciente(data, file);
    }

    public DataPaciente cargar() {
        DataPaciente data = XMLHelper.getInstancia().cargarPaciente(file);
        return data != null ? data : new DataPaciente();
    }

}
