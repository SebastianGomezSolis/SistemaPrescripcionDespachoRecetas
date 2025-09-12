package com.sistema.sistemaprescripciondespachorecetas.logic.Mapper;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.MedicamentoEntity;
import com.sistema.sistemaprescripciondespachorecetas.model.Medicamento;

public class MedicamentoMapper {
    public static MedicamentoEntity toXml(com.sistema.sistemaprescripciondespachorecetas.model.Medicamento a) {
        if (a == null) return null;
        MedicamentoEntity me = new MedicamentoEntity();
        me.setCodigo(a.getCodigo());
        me.setNombre(a.getNombre());
        me.setDescripcion(a.getDescripcion());
        return me;
    }
    public static Medicamento toModel(MedicamentoEntity a) {
        if (a == null) return null;
        Medicamento me = new Medicamento(
                a.getCodigo(),
                a.getNombre(),
                a.getDescripcion()
        );
        me.setCodigo(a.getCodigo());
        me.setDescripcion(a.getDescripcion());
        me.setNombre(a.getNombre());
        return me;
    }
}
