package com.sistema.sistemaprescripciondespachorecetas.logic.Mapper;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.RecetaEntity;
import com.sistema.sistemaprescripciondespachorecetas.model.Receta;

public class RecetaMapper {
    public static RecetaEntity toXml(Receta r) {
        if (r == null) { return null; }
        RecetaEntity receta = new RecetaEntity();
        receta.setId(r.getId());
        receta.setPaciente(PacienteMapper.toXml(r.getPaciente()));
        receta.setMedicamento(MedicamentoMapper.toXml(r.getMedicamento()));
        receta.setEstado(r.getEstado());
        receta.setCantidad(r.getCantidad());
        receta.setIndicaciones(r.getIndicaciones());
        receta.setDiasDuracion(r.getDiasDuracion());
        receta.setFechaEntrega(r.getFechaEntrega());
        return receta;
    }

    public static Receta toModel(RecetaEntity receta) {
        if (receta == null) { return null; }
        Receta r = new Receta(
                receta.getId(),
                PacienteMapper.toModel(receta.getPaciente()),
                MedicamentoMapper.toModel(receta.getMedicamento()),
                receta.getFechaEntrega(),
                receta.getCantidad(),
                receta.getIndicaciones(),
                receta.getDiasDuracion()
        );
        r.setEstado(receta.getEstado());
        return r;
    }
}
