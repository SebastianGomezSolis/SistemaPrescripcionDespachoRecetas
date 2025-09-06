package com.sistema.sistemaprescripciondespachorecetas.datos.conector;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.AdministradorEntity;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "administradoresData")
@XmlAccessorType(XmlAccessType.FIELD)
public class AdministradorConector {
    @XmlElementWrapper(name = "administradores")
    @XmlElement(name = "administrador")
    private List<AdministradorEntity> administradores = new ArrayList<>();

    // Getters y Setters
    public List<AdministradorEntity> getAdministradores() { return administradores; }
    public void setAdministradores(List<AdministradorEntity> administradores) { this.administradores = administradores; }

}
