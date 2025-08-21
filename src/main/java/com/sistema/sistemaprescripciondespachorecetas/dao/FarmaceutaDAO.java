package com.sistema.sistemaprescripciondespachorecetas.dao;

import com.sistema.sistemaprescripciondespachorecetas.data.DataFarmauceuta;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;
import com.sistema.sistemaprescripciondespachorecetas.logic.Farmaceuta;

public class FarmaceutaDAO {
    private static final String file = "farmaceutas.xml";

    public void guardar(DataFarmauceuta data) {
        XMLHelper.getInstancia().guardarFarmauceuta(data, file);
    }

    public DataFarmauceuta cargar() {
        DataFarmauceuta data = XMLHelper.getInstancia().cargarFarmauceuta(file);
        return data != null ? data : new DataFarmauceuta();
    }

    public void agregarFarmaceuta(Farmaceuta farmaceuta) {
        DataFarmauceuta data = cargar();
        data.agregarFarmaceuta(farmaceuta);
        guardar(data);
    }

    public Farmaceuta buscarFarmaceutaPorId(String id) {
        return cargar().buscarFarmaceutaPorId(id);
    }

    public Farmaceuta buscarFarmaceutaPorNombre(String nombre) {
        return cargar().buscarFarmaceutaPorNombre(nombre);
    }

    public boolean actualizarFarmaceuta(Farmaceuta farmaceutaModificado) {
        DataFarmauceuta data = cargar();
        if (data.modificarFarmaceuta(farmaceutaModificado)) {
            guardar(data);
            return true;
        }
        return false;
    }

    public boolean actualizarClave(String id, String claveActual, String nuevaClave) {
        DataFarmauceuta data = cargar();
        if (data.actualizarClave(id, claveActual, nuevaClave)) {
            guardar(data);
            return true;
        }
        return false;
    }

    public void eliminarFarmaceuta(String id) {
        DataFarmauceuta data = cargar();
        data.eliminarFarmaceuta(id);
        guardar(data);
    }

}
