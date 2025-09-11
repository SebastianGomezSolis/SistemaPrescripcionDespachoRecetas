package com.sistema.sistemaprescripciondespachorecetas.logic.Mapper;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.FarmaceutaEntity;
import com.sistema.sistemaprescripciondespachorecetas.model.Farmaceuta;

public class FarmaceutaMapper {
    public static FarmaceutaEntity toXml(com.sistema.sistemaprescripciondespachorecetas.model.Farmaceuta a) {
        if (a == null) return null;
        FarmaceutaEntity fe = new FarmaceutaEntity();
        fe.setId(a.getId());
        fe.setClave(a.getClave());
        fe.setNombre(a.getNombre());
        return fe;
    }
    public static com.sistema.sistemaprescripciondespachorecetas.model.Farmaceuta toModel(FarmaceutaEntity a) {
        if (a == null) return null;
        Farmaceuta fe = new Farmaceuta(
                a.getId(),
                a.getClave(),
                a.getNombre()
        );
        return fe;
    }
}
