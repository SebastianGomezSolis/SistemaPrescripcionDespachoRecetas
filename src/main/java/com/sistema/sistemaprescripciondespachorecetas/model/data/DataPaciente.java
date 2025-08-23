package com.sistema.sistemaprescripciondespachorecetas.model.data;

import com.sistema.sistemaprescripciondespachorecetas.model.logic.Paciente;

import java.util.ArrayList;
import java.util.List;

public class DataPaciente {
    private List<Paciente> pacientes;

    public DataPaciente() { pacientes = new ArrayList<>(); }

    public void agregarPaciente(Paciente paciente) { pacientes.add(paciente); }

    public void eliminarPaciente(String id) { pacientes.removeIf(paciente -> paciente.getId().equals(id)); }

    public Paciente buscarPacientePorID(String id) {
        for (Paciente paciente : pacientes) {
            if (paciente.getId().equals(id)) {
                return paciente;
            }
        }
        return null;
    }

    public Paciente buscarPacientePorNombre(String nombre) {
        for (Paciente paciente : pacientes) {
            if (paciente.getNombre().equals(nombre)) {
                return paciente;
            }
        }
        return null;
    }

    public boolean modificarPaciente(Paciente pacienteModificado) {
        for (int i = 0; i < pacientes.size(); i++) {
            if (pacientes.get(i).getId().equals(pacienteModificado.getId())) {
                pacientes.set(i, pacienteModificado);
                return true;
            }
        }
        return false;
    }

    public List<Paciente> getPacientes() { return pacientes; }

}
