package com.sistema.sistemaprescripciondespachorecetas.dao;

import com.sistema.sistemaprescripciondespachorecetas.data.DataPaciente;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;
import com.sistema.sistemaprescripciondespachorecetas.logic.Paciente;

public class PacienteDAO {
    private static final String file = "pacientes.xml";

    public void guardar(DataPaciente data) {
        XMLHelper.getInstancia().guardarPaciente(data, file);
    }

    public DataPaciente cargar() {
        DataPaciente data = XMLHelper.getInstancia().cargarPaciente(file);
        return data != null ? data : new DataPaciente();
    }

    public void agregarPaciente(Paciente paciente) {
        DataPaciente data = cargar();
        data.agregarPaciente(paciente);
        guardar(data);
    }

    public Paciente buscarPacientePorId(String id) {
        return cargar().buscarPacientePorID(id);
    }

    public Paciente buscarPacientePorNombre(String nombre) {
        return cargar().buscarPacientePorNombre(nombre);
    }

    public boolean actualizarPaciente(Paciente pacienteModificado) {
        DataPaciente data = cargar();
        if (data.modificarPaciente(pacienteModificado)) {
            guardar(data);
            return true;
        }
        return false;
    }

    public void eliminarPaciente(String id) {
        DataPaciente data = cargar();
        data.eliminarPaciente(id);
        guardar(data);
    }

}
