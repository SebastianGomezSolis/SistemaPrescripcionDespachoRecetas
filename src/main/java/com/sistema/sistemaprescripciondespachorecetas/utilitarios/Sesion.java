package com.sistema.sistemaprescripciondespachorecetas.utilitarios;

import java.util.ArrayList;
import java.util.List;

public class Sesion {
    private static String usuarioId;
    private static List<String> codigosPermitidos = new ArrayList<>();

    public static void iniciarSesion(String id, List<String> codigos) {
        usuarioId = id;
        codigosPermitidos = codigos;
    }

    public static boolean puedeAccederModulo(String codigoModulo) {
        return codigosPermitidos.contains(codigoModulo);
    }

    public static String getUsuarioId() {
        return usuarioId;
    }
}
