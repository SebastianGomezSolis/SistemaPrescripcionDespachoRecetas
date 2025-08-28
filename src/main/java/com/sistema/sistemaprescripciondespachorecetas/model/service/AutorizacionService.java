package com.sistema.sistemaprescripciondespachorecetas.model.service;

import com.sistema.sistemaprescripciondespachorecetas.model.logic.Usuario;

public class AutorizacionService {
    private LoginService loginService;

    // Definición de funcionalidades del sistema
    public static final String funcionalidadPrescripcion = "Prescripcion";
    public static final String funcionalidadDespacho = "Despacho";
    public static final String funcionalidadListaMedicos = "Lista_Medicos";
    public static final String funcionalidadListaFarmaceutas = "Lista_Farmaceutas";
    public static final String funcionalidadListaPacientes = "Lista_Pacientes";
    public static final String funcionalidadCatalogoMedicamentos = "Catalogo_Medicamentos";
    public static final String funcionalidadDashboard = "Dashboard";
    public static final String funcionalidadHistoricoRecetas = "Historico_Recetas";
    public static final String funcionalidadCambioClave = "Cambio_Clave";

    public AutorizacionService() {
        this.loginService = new LoginService();
    }

    public boolean tieneAcceso(Usuario usuario, String funcionalidad) {
        if (usuario == null || funcionalidad == null) {
            return false;
        }

        String tipoUsuario = loginService.obtenerTipoUsuario(usuario);

        switch (funcionalidad) {
            case funcionalidadPrescripcion:
                return esAccesoMedicos(tipoUsuario);

            case funcionalidadDespacho:
                return esAccesoFarmaceutas(tipoUsuario);

            case funcionalidadListaMedicos:
            case funcionalidadListaFarmaceutas:
            case funcionalidadListaPacientes:
            case funcionalidadCatalogoMedicamentos:
                return esAccesoAdministradores(tipoUsuario);

            case funcionalidadDashboard:
            case funcionalidadHistoricoRecetas:
                return esAccesoTodos(tipoUsuario);

            case funcionalidadCambioClave:
                return esAccesoTodos(tipoUsuario);

            default:
                return false;
        }
    }

    public void validarAcceso(Usuario usuario, String funcionalidad) throws SecurityException {
        if (!tieneAcceso(usuario, funcionalidad)) {
            String tipoUsuario = usuario != null ? loginService.obtenerTipoUsuario(usuario) : "Desconocido";
            throw new SecurityException(String.format("El usuario tipo '%s' no tiene acceso a la funcionalidad '%s'", tipoUsuario, funcionalidad)
            );
        }
    }

    public String obtenerMensajePermisos(String funcionalidad) {
        switch (funcionalidad) {
            case funcionalidadPrescripcion:
                return "Esta funcionalidad solo está disponible para médicos";

            case funcionalidadDespacho:
                return "Esta funcionalidad solo está disponible para farmaceutas";

            case funcionalidadListaMedicos:
                return "La gestión de médicos solo está disponible para administradores";

            case funcionalidadListaFarmaceutas:
                return "La gestión de farmaceutas solo está disponible para administradores";

            case funcionalidadListaPacientes:
                return "La gestión de pacientes solo está disponible para administradores";

            case funcionalidadCatalogoMedicamentos:
                return "La gestión del catálogo de medicamentos solo está disponible para administradores";

            case funcionalidadDashboard:
                return "El dashboard está disponible para todos los usuarios autenticados";

            case funcionalidadHistoricoRecetas:
                return "El histórico de recetas está disponible para todos los usuarios autenticados";

            case funcionalidadCambioClave:
                return "El cambio de clave está disponible para todos los usuarios autenticados";

            default:
                return "Funcionalidad no reconocida";
        }
    }

    public String[] obtenerFuncionalidadesDisponibles(Usuario usuario) {
        if (usuario == null) {
            return new String[0];
        }

        String tipoUsuario = loginService.obtenerTipoUsuario(usuario);

        switch (tipoUsuario) {
            case LoginService.tipoAdministrador:
                return new String[]{
                        funcionalidadListaMedicos,
                        funcionalidadListaFarmaceutas,
                        funcionalidadListaPacientes,
                        funcionalidadCatalogoMedicamentos,
                        funcionalidadDashboard,
                        funcionalidadHistoricoRecetas,
                        funcionalidadCambioClave
                };

            case LoginService.tipoMedico:
                return new String[]{
                        funcionalidadPrescripcion,
                        funcionalidadDashboard,
                        funcionalidadHistoricoRecetas,
                        funcionalidadCambioClave
                };

            case LoginService.tipoFarmaceuta:
                return new String[]{
                        funcionalidadDespacho,
                        funcionalidadDashboard,
                        funcionalidadHistoricoRecetas,
                        funcionalidadCambioClave
                };

            default:
                return new String[0];
        }
    }

    public boolean esUsuarioValido(Usuario usuario) {
        return usuario != null &&
                !LoginService.tipoDesconocido.equals(loginService.obtenerTipoUsuario(usuario));
    }

    // Métodos privados para verificar tipos de acceso
    private boolean esAccesoAdministradores(String tipoUsuario) { return LoginService.tipoAdministrador.equals(tipoUsuario);}

    private boolean esAccesoMedicos(String tipoUsuario) { return LoginService.tipoMedico.equals(tipoUsuario); }

    private boolean esAccesoFarmaceutas(String tipoUsuario) { return LoginService.tipoFarmaceuta.equals(tipoUsuario); }

    private boolean esAccesoTodos(String tipoUsuario) { return LoginService.tipoAdministrador.equals(tipoUsuario) || LoginService.tipoMedico.equals(tipoUsuario) || LoginService.tipoFarmaceuta.equals(tipoUsuario); }

}
