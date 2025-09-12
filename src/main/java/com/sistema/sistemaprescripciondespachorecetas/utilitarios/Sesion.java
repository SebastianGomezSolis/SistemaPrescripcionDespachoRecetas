package com.sistema.sistemaprescripciondespachorecetas.utilitarios;

import com.sistema.sistemaprescripciondespachorecetas.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Sesion {
    private static String usuarioId;
    private static Usuario usuarioActual;
    private static List<String> codigosPermitidos = new ArrayList<>();

    public static void iniciarSesion(Usuario usuario, List<String> codigos) {
        usuarioActual = usuario;
        usuarioId = usuario.getId();
        codigosPermitidos = codigos;
    }

    public static boolean puedeAccederModulo(String codigoModulo) {
        return codigosPermitidos.contains(codigoModulo);
    }

    public static String getUsuarioId() {
        return usuarioId;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    //Actualizar el usuario en la sesión actual
    public static void actualizarUsuario(Usuario usuarioActualizado) {
        if (usuarioActual != null && usuarioActual.getId().equals(usuarioActualizado.getId())) {
            usuarioActual = usuarioActualizado;
        }
    }

    // Cerrar sesión
    public static void cerrarSesion() {
        usuarioActual = null;
        usuarioId = null;
        codigosPermitidos.clear();
    }

    //  Verificar si hay sesión activa
    public static boolean hayUsuarioLogueado() {
        return usuarioActual != null;
    }
}