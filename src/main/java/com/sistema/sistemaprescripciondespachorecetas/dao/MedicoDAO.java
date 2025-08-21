package com.sistema.sistemaprescripciondespachorecetas.dao;

import com.sistema.sistemaprescripciondespachorecetas.data.DataMedico;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;
import com.sistema.sistemaprescripciondespachorecetas.logic.Medico;

public class MedicoDAO {
    private static final String file = "medicos.xml";

    public void guardar(DataMedico data) {
        XMLHelper.getInstancia().guardarMedico(data, file);
    }

    public DataMedico cargar() {
        DataMedico data = XMLHelper.getInstancia().cargarMedico(file);
        return data != null ? data : new DataMedico();
    }

    public void agregarMedico(Medico medico) {
        DataMedico data = cargar();
        data.agregarMedico(medico);
        guardar(data);
    }

    public Medico buscarMedicoPorId(String id) {
        return cargar().buscarMedicoPorId(id);
    }

    public Medico buscarMedicoPorNombre(String nombre) {
        return cargar().buscarMedicoPorNombre(nombre);
    }

    public boolean actualizarMedico(Medico medicoModificado) {
        DataMedico data = cargar();
        if (data.modificarMedico(medicoModificado)) {
            guardar(data);
            return true;
        }
        return false;
    }

    public boolean actualizarClave(String id, String claveActual, String nuevaClave) {
        DataMedico data = cargar();
        if (data.actualizarClave(id, claveActual, nuevaClave)) {
            guardar(data);
            return true;
        }
        return false;
    }

    public void eliminarMedico(String id) {
        DataMedico data = cargar();
        data.eliminarMedico(id);
        guardar(data);
    }

}
