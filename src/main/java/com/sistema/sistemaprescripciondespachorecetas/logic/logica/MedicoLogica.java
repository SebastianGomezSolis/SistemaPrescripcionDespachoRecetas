package com.sistema.sistemaprescripciondespachorecetas.logic.logica;

import com.sistema.sistemaprescripciondespachorecetas.model.Medico;
import java.util.ArrayList;
import java.util.List;

public class MedicoLogica {
    private static final List<Medico> listaMedicos = new ArrayList<>();

    public void create(Medico medico) throws Exception {
        for (Medico m : listaMedicos) {
            if (m.getId().equals(medico.getId())) {
                throw new Exception("Ya existe un médico con este ID");
            }
        }
        listaMedicos.add(medico);
    }

    public List<Medico> findAll() {
        return new ArrayList<>(listaMedicos);
    }

    public Medico findById(String id) {
        for (Medico m : listaMedicos) {
            if (m.getId().equals(id)) {
                return m;
            }
        }
        return null;
    }

    public void update(Medico medico) throws Exception {
        for (int i = 0; i < listaMedicos.size(); i++) {
            if (listaMedicos.get(i).getId().equals(medico.getId())) {
                listaMedicos.set(i, medico);
                return;
            }
        }
        throw new Exception("No existe un médico con este ID");
    }

    public void deleteById(String id) throws Exception {
        Medico encontrado = null;
        for (Medico m : listaMedicos) {
            if (m.getId().equals(id)) {
                encontrado = m;
                break;
            }
        }
        if (encontrado != null) {
            listaMedicos.remove(encontrado);
        } else {
            throw new Exception("No existe un médico con este ID");
        }
    }
}
