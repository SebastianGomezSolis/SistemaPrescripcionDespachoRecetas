package com.sistema.sistemaprescripciondespachorecetas.logic.Mapper;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.RecetaDetalleEntity;
import com.sistema.sistemaprescripciondespachorecetas.datos.entity.RecetaEntity;
import com.sistema.sistemaprescripciondespachorecetas.model.Receta;
import com.sistema.sistemaprescripciondespachorecetas.model.RecetaDetalle;

import java.util.List;
import java.util.stream.Collectors;

public class RecetaMapper {
    public static RecetaEntity toXml(Receta r) {
        if (r == null) return null;

        RecetaEntity receta = new RecetaEntity();
        receta.setId(r.getId());
        receta.setPaciente(PacienteMapper.toXml(r.getPaciente()));
        receta.setFechaEntrega(r.getFechaEntrega());
        receta.setEstado(r.getEstado());

        // Mapea la lista de RecetaDetalle -> RecetaDetalleEntity
        if (r.getMedicamentos() != null) {
            List<RecetaDetalleEntity> detalles = r.getMedicamentos().stream()
                    .map(RecetaMapper::toXmlDetalle)
                    .collect(Collectors.toList());
            receta.setMedicamentos(detalles);
        }

        return receta;
    }

    public static Receta toModel(RecetaEntity receta) {
        if (receta == null) return null;

        Receta r = new Receta();
        r.setId(receta.getId());
        r.setPaciente(PacienteMapper.toModel(receta.getPaciente()));
        r.setFechaEntrega(receta.getFechaEntrega());
        r.setEstado(receta.getEstado());

        // Mapea la lista de RecetaDetalleEntity -> RecetaDetalle
        if (receta.getMedicamentos() != null) {
            List<RecetaDetalle> detalles = receta.getMedicamentos().stream()
                    .map(RecetaMapper::toModelDetalle)
                    .collect(Collectors.toList());
            r.setMedicamentos(detalles);
        }

        return r;
    }

    public static RecetaDetalleEntity toXmlDetalle(RecetaDetalle detalle) {
        if (detalle == null) return null;

        RecetaDetalleEntity entity = new RecetaDetalleEntity();
        entity.setMedicamento(MedicamentoMapper.toXml(detalle.getMedicamento()));
        entity.setCantidad(detalle.getCantidad());
        entity.setIndicaciones(detalle.getIndicaciones());
        entity.setDiasDuracion(detalle.getDiasDuracion());
        return entity;
    }

    public static RecetaDetalle toModelDetalle(RecetaDetalleEntity entity) {
        if (entity == null) return null;

        RecetaDetalle detalle = new RecetaDetalle();
        detalle.setMedicamento(MedicamentoMapper.toModel(entity.getMedicamento()));
        detalle.setCantidad(entity.getCantidad());
        detalle.setIndicaciones(entity.getIndicaciones());
        detalle.setDiasDuracion(entity.getDiasDuracion());
        return detalle;
    }
}
