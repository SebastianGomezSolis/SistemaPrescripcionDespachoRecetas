package com.sistema.sistemaprescripciondespachorecetas.data;

import com.sistema.sistemaprescripciondespachorecetas.logic.Medico;

import java.util.ArrayList;
import java.util.List;

public class DataMedico {
    private List<Medico> medicos;

    public DataMedico() { medicos = new ArrayList<>(); }

    public void agregarMedico(Medico medico) { medicos.add(medico); }

    public void eliminarMedico(String id) { medicos.removeIf(medico -> medico.getId().equals(id)); }

    public Medico buscarMedico(String id) {
        for (Medico medico : medicos) {
            if (medico.getId().equals(id)) {
                return medico;
            }
        }
        return null;
    }

    public void modificarMedico(Medico medicoModificado) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId().equals(medicoModificado.getId())) {
                medicos.set(i, medicoModificado);
                return;
            }
        }
    }

    public List<Medico> getMedicos() { return medicos; }

}
