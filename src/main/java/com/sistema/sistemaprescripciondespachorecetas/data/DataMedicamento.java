package com.sistema.sistemaprescripciondespachorecetas.data;

import com.sistema.sistemaprescripciondespachorecetas.logic.Medicamento;

import java.util.ArrayList;
import java.util.List;

public class DataMedicamento {
    private List<Medicamento> medicamentos;

    public DataMedicamento() { medicamentos = new ArrayList<>(); }

    public void agregarMedicamento(Medicamento medicamento) { medicamentos.add(medicamento); }

    public void eliminarMedicamento(String codigo) { medicamentos.removeIf(medicamento -> medicamento.getCodigo().equals(codigo)); }

    public Medicamento buscarMedicamentoPorCodigo(String codigo) {
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getCodigo().equals(codigo)) {
                return medicamento;
            }
        }
        return null;
    }

    public Medicamento buscarMedicamentoPorNombre(String nombre) {
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getNombre().equals(nombre)) {
                return medicamento;
            }
        }
        return null;
    }

    public boolean modificarMedicamento(Medicamento medicamentoModificado) {
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getCodigo().equals(medicamentoModificado.getCodigo())) {
                medicamentos.set(i, medicamentoModificado);
                return true;
            }
        }
        return false;
    }

    public List<Medicamento> getMedicamentos() { return medicamentos; }

}
