package com.sistema.sistemaprescripciondespachorecetas.datos.conector;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.RecetaEntity;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "recetasData")
@XmlAccessorType(XmlAccessType.FIELD)
public class RecetaConector {
    @XmlElementWrapper(name = "recetas")
    @XmlElement(name = "receta")
    private List<RecetaEntity> recetas = new ArrayList<>();

    // Getters y Setters
    public List<RecetaEntity> getRecetas() { return recetas; }
    public void setRecetas(List<RecetaEntity> recetas) { this.recetas = recetas; }

}
