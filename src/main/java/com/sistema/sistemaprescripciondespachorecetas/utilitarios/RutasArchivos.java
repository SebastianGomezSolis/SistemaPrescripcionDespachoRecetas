package com.sistema.sistemaprescripciondespachorecetas.utilitarios;

import java.nio.file.Paths;

public class RutasArchivos {
    public static final String MEDICOS = Paths.get(System.getProperty("user.dir"), "bd", "medicos.xml").toString();
    public static final String FARMACEUTAS = Paths.get(System.getProperty("user.dir"), "bd", "farmaceutas.xml").toString();
    public static final String ADMINISTRADORES = Paths.get(System.getProperty("user.dir"), "bd", "administradores.xml").toString();
}
