package com.sistema.sistemaprescripciondespachorecetas.logic.Mapper;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.MedicoEntity;
import com.sistema.sistemaprescripciondespachorecetas.model.Medico;

public class MedicoMapper {
    public static MedicoEntity toXml(Medico a) {
        if (a == null) return null;
        MedicoEntity me = new MedicoEntity();
        me.setId(a.getId());
        me.setClave(a.getClave());
        me.setNombre(a.getNombre());
        me.setEspecialidad(a.getEspecialidad());
        return me;
    }

    public static Medico toModel(MedicoEntity a) {
        if (a == null) return null;
        Medico me = new Medico(
                a.getId(),
                a.getClave(),
                a.getNombre(),
                a.getEspecialidad()
        );
        return me;
    }
}
