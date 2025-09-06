package com.sistema.sistemaprescripciondespachorecetas.datos.conector;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.MedicoEntity;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "medicosData")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedicoConector {
    @XmlElementWrapper(name = "medicos")
    @XmlElement(name = "medico")
    private List<MedicoEntity> medicos;

    // Getters y Setters
    public List<MedicoEntity> getMedicos() { return medicos; }
    public void setMedicos(List<MedicoEntity> medicos) { this.medicos = medicos; }
}
