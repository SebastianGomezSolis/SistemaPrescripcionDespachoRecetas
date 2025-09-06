package com.sistema.sistemaprescripciondespachorecetas.datos.conector;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.FarmaceutaEntity;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "farmaceutasData")
@XmlAccessorType(XmlAccessType.FIELD)
public class FarmaceutaConector {
    @XmlElementWrapper(name = "farmaceutas")
    @XmlElement(name = "farmaceuta")
    private List<FarmaceutaEntity> farmaceutas = new ArrayList<>();

    // Getters y Setters
    public List<FarmaceutaEntity> getFarmaceutas() { return farmaceutas; }
    public void setFarmaceutas(List<FarmaceutaEntity> farmaceutas) { this.farmaceutas = farmaceutas; }

}
