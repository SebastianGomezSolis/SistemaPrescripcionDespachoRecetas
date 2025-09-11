package com.sistema.sistemaprescripciondespachorecetas.logic.Mapper;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.PacienteEntity;
import com.sistema.sistemaprescripciondespachorecetas.model.Paciente;

public class PacienteMapper {
    public static PacienteEntity toXml(Paciente p) {
        if(p == null) return null;
        PacienteEntity pe = new PacienteEntity();
        pe.setId(p.getId());
        pe.setNombre(p.getNombre());
        pe.setTelefono(p.getTelefono());
        pe.setFechaNacimiento(p.getFechaNacimiento());
        return pe;
    }

    public static Paciente toModel(PacienteEntity pe) {
        if(pe == null) return null;
        Paciente p = new Paciente(
                pe.getId(),
                pe.getNombre(),
                pe.getFechaNacimiento(),
                pe.getTelefono()
        );
        return p;
    }
}
