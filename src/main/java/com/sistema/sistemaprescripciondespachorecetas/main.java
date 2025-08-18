package com.sistema.sistemaprescripciondespachorecetas;

import com.sistema.sistemaprescripciondespachorecetas.data.DataAdministrador;
import com.sistema.sistemaprescripciondespachorecetas.data.XMLHelper;
import com.sistema.sistemaprescripciondespachorecetas.logic.Administrador;

public class main {
    public static void main(String[] args) {
        DataAdministrador data = new DataAdministrador();
        data.agregarAdministrador(new Administrador("12345678", "123456"));

        XMLHelper.getInstancia().guardarAdministrador(data, "administradores.xml");
    }
}
