package com.sistema.sistemaprescripciondespachorecetas.data;

import com.sistema.sistemaprescripciondespachorecetas.logic.Farmaceuta;

import java.util.ArrayList;
import java.util.List;

public class DataFarmauceuta {
    private List<Farmaceuta> farmaceutas;

    public DataFarmauceuta() { farmaceutas = new ArrayList<>(); }

    public void agregarFarmaceuta(Farmaceuta farmaceuta) { farmaceutas.add(farmaceuta); }

    public void eliminarFarmaceuta(String id) { farmaceutas.removeIf(farmaceuta -> farmaceuta.getId().equals(id)); }

    public Farmaceuta buscarFarmaceutaPorId(String id) {
        for (Farmaceuta farmaceuta : farmaceutas) {
            if (farmaceuta.getId().equals(id)) {
                return farmaceuta;
            }
        }
        return null;
    }

    public Farmaceuta buscarFarmaceutaPorNombre(String nombre) {
        for (Farmaceuta farmaceuta : farmaceutas) {
            if (farmaceuta.getNombre().equals(nombre)) {
                return farmaceuta;
            }
        }
        return null;
    }

    public boolean modificarFarmaceuta(Farmaceuta farmaceutaModificada) {
        for (int i = 0; i < farmaceutas.size(); i++) {
            if (farmaceutas.get(i).getId().equals(farmaceutaModificada.getId())) {
                farmaceutas.set(i, farmaceutaModificada);
                return true;
            }
        }
        return false;
    }

    public boolean verificarClave(String id, String clave) {
        for (Farmaceuta farmaceuta : farmaceutas) {
            if (farmaceuta.getId().equals(id) && farmaceuta.getClave().equals(clave)) {
                return true;
            }
        }
        return false;
    }

    public boolean actualizarClave(String id, String claveActual, String nuevaClave) {
        for (Farmaceuta farmaceuta : farmaceutas) {
            if (farmaceuta.getId().equals(id)) {
                if (verificarClave(id, claveActual)) {
                    farmaceuta.setClave(nuevaClave);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Farmaceuta> getFarmaceutas() { return farmaceutas; }

}
