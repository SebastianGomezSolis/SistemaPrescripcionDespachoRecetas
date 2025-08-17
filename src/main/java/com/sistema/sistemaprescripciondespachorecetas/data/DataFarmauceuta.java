package com.sistema.sistemaprescripciondespachorecetas.data;

import com.sistema.sistemaprescripciondespachorecetas.model.Farmaceuta;

import java.util.ArrayList;
import java.util.List;

public class DataFarmauceuta {
    private List<Farmaceuta> farmaceutas;

    public DataFarmauceuta() { farmaceutas = new ArrayList<>(); }

    public void agregarFarmaceuta(Farmaceuta farmaceuta) { farmaceutas.add(farmaceuta); }

    public void eliminarFarmaceuta(String id) { farmaceutas.removeIf(farmaceuta -> farmaceuta.getId().equals(id)); }

    public Farmaceuta buscarFarmaceuta(String id) {
        for (Farmaceuta farmaceuta : farmaceutas) {
            if (farmaceuta.getId().equals(id)) {
                return farmaceuta;
            }
        }
        return null;
    }

    public void modificarFarmaceuta(Farmaceuta farmaceutaModificada) {
        for (int i = 0; i < farmaceutas.size(); i++) {
            if (farmaceutas.get(i).getId().equals(farmaceutaModificada.getId())) {
                farmaceutas.set(i, farmaceutaModificada);
                return;
            }
        }
    }

    public List<Farmaceuta> getFarmaceutas() { return farmaceutas; }

}
