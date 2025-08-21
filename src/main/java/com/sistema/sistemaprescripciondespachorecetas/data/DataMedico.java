package com.sistema.sistemaprescripciondespachorecetas.data;

import com.sistema.sistemaprescripciondespachorecetas.logic.Medico;

import java.util.ArrayList;
import java.util.List;

public class DataMedico {
    private List<Medico> medicos;

    public DataMedico() { medicos = new ArrayList<>(); }

    public void agregarMedico(Medico medico) { medicos.add(medico); }

    public void eliminarMedico(String id) { medicos.removeIf(medico -> medico.getId().equals(id)); }

    public Medico buscarMedicoPorId(String id) {
        for (Medico medico : medicos) {
            if (medico.getId().equals(id)) {
                return medico;
            }
        }
        return null;
    }

    public Medico buscarMedicoPorNombre(String nombre) {
        for (Medico medico : medicos) {
            if (medico.getNombre().equals(nombre)) {
                return medico;
            }
        }
        return null;
    }

    public boolean modificarMedico(Medico medicoModificado) {
        for (int i = 0; i < medicos.size(); i++) {
            if (medicos.get(i).getId().equals(medicoModificado.getId())) {
                medicos.set(i, medicoModificado);
                return true;
            }
        }
        return false;
    }

    public boolean verificarClave(String id, String clave) {
        for (Medico medico : medicos) {
            if (medico.getId().equals(id) && medico.getClave().equals(clave)) {
                return true;
            }
        }
        return false;
    }

    public boolean actualizarClave(String id, String claveActual, String nuevaClave) {
        for (Medico medico : medicos) {
            if (medico.getId().equals(id)) {
                if (verificarClave(id, claveActual)) {
                    medico.setClave(nuevaClave);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Medico> getMedicos() { return medicos; }

}
