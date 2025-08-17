package com.sistema.sistemaprescripciondespachorecetas.data;

import com.sistema.sistemaprescripciondespachorecetas.model.Medicamento;

import java.util.ArrayList;
import java.util.List;

public class DataMedicamento {
    private List<Medicamento> medicamentos;

    public DataMedicamento() { medicamentos = new ArrayList<>(); }

    public void agregarMedicamento(Medicamento medicamento) { medicamentos.add(medicamento); }

    public void eliminarMedicamento(String codigo) { medicamentos.removeIf(medicamento -> medicamento.getCodigo().equals(codigo)); }

    public Medicamento buscarMedicamento(String codigo) {
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getCodigo().equals(codigo)) {
                return medicamento;
            }
        }
        return null;
    }

    public void modificarMedicamento(Medicamento medicamentoModificado) {
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getCodigo().equals(medicamentoModificado.getCodigo())) {
                medicamentos.set(i, medicamentoModificado);
                return;
            }
        }
    }

    public List<Medicamento> getMedicamentos() { return medicamentos; }

}
