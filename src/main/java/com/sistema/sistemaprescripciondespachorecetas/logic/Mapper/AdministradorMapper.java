package com.sistema.sistemaprescripciondespachorecetas.logic.Mapper;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.AdministradorEntity;
import com.sistema.sistemaprescripciondespachorecetas.model.Administrador;

public class AdministradorMapper {
    public static AdministradorEntity toXml(Administrador a) {
        if (a == null) return null;
        AdministradorEntity ad = new AdministradorEntity();
        ad.setId(a.getId());
        ad.setClave(a.getClave());
        return ad;
    }

    public static Administrador toModel(AdministradorEntity ad) {
        if (ad == null) return null;
        Administrador a = new Administrador(
                ad.getId(),
                ad.getClave()
        );
        return a;
    }
}
