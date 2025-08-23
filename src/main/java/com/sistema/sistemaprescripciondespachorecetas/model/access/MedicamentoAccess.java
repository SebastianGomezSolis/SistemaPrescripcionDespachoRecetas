package com.sistema.sistemaprescripciondespachorecetas.model.access;

import com.sistema.sistemaprescripciondespachorecetas.model.data.DataMedicamento;
import com.sistema.sistemaprescripciondespachorecetas.model.data.XMLHelper;
import com.sistema.sistemaprescripciondespachorecetas.model.logic.Medicamento;

public class MedicamentoAccess {
    private static final String file = "medicamentos.xml";

    public void guardar(DataMedicamento data) {
        XMLHelper.getInstancia().guardarMedicamento(data, file);
    }

    public DataMedicamento cargar() {
        DataMedicamento data = XMLHelper.getInstancia().cargarMedicamento(file);
        return data != null ? data : new DataMedicamento();
    }

    public void agregarMedicamento(Medicamento medicamento) {
        DataMedicamento data = cargar();
        data.agregarMedicamento(medicamento);
        guardar(data);
    }

    public Medicamento buscarMedicamentoPorCodigo(String codigo) {
        return cargar().buscarMedicamentoPorCodigo(codigo);
    }

    public Medicamento buscarMedicamentoPorNombre(String nombre) {
        return cargar().buscarMedicamentoPorNombre(nombre);
    }

    public boolean actualizarMedicamento(Medicamento medicamentoModificado) {
        DataMedicamento data = cargar();
        if (data.modificarMedicamento(medicamentoModificado)) {
            guardar(data);
            return true;
        }
        return false;
    }

    public void eliminarMedicamento(String codigo) {
        DataMedicamento data = cargar();
        data.eliminarMedicamento(codigo);
        guardar(data);
    }

}
