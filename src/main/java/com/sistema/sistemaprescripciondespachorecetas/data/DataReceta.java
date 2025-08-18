package com.sistema.sistemaprescripciondespachorecetas.data;

import com.sistema.sistemaprescripciondespachorecetas.logic.Receta;

import java.util.ArrayList;
import java.util.List;

public class DataReceta {
    private List<Receta> recetas;

    public DataReceta() { recetas = new ArrayList<>(); }

    public void agregarReceta(Receta receta) { recetas.add(receta); }

    public void eliminarReceta(String id) { recetas.removeIf(receta -> receta.getId().equals(id)); }

    public Receta buscarReceta(String id) {
        for (Receta receta : recetas) {
            if (receta.getId().equals(id)) {
                return receta;
            }
        }
        return null;
    }

    public void modificarReceta(Receta recetaModificada) {
        for (int i = 0; i < recetas.size(); i++) {
            if (recetas.get(i).getId().equals(recetaModificada.getId())) {
                recetas.set(i, recetaModificada);
                return;
            }
        }
    }

    public List<Receta> getRecetas() { return recetas; }
}
