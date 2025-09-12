package com.sistema.sistemaprescripciondespachorecetas.logic.logica;

import com.sistema.sistemaprescripciondespachorecetas.datos.conector.AdministradorConector;
import com.sistema.sistemaprescripciondespachorecetas.datos.dato.AdministradorDatos;
import com.sistema.sistemaprescripciondespachorecetas.datos.entity.AdministradorEntity;
import com.sistema.sistemaprescripciondespachorecetas.logic.Mapper.AdministradorMapper;
import com.sistema.sistemaprescripciondespachorecetas.model.Administrador;

import java.util.List;
import java.util.stream.Collectors;

public class AdministradorLogica {

    private final AdministradorDatos store;

    public AdministradorLogica(String rutaArchivo) {
        this.store = new AdministradorDatos(rutaArchivo);
    }

    // --------- Lectura ---------

    public List<Administrador> findAll() {
        AdministradorConector data = store.load();
        return data.getAdministradores().stream()
                .map(AdministradorMapper::toModel)
                .collect(Collectors.toList());
    }

    public Administrador findById(String id) {
        AdministradorConector data = store.load();
        return data.getAdministradores().stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .map(AdministradorMapper::toModel)
                .orElse(null);
    }

    // --------- Escritura ---------

    public Administrador create(Administrador nuevo) throws Exception {
        validarNuevo(nuevo);
        AdministradorConector data = store.load();

        boolean existsId = data.getAdministradores().stream()
                .anyMatch(x -> nuevo.getId().equals(x.getId()));

        if (existsId) {
            throw new Exception("Ya existe un administrador con este ID: " + nuevo.getId());
        }

        AdministradorEntity entity = AdministradorMapper.toXml(nuevo);
        data.getAdministradores().add(entity);
        store.save(data);
        return AdministradorMapper.toModel(entity);
    }

    public Administrador update(Administrador admin) throws Exception {
        if (admin == null || admin.getId() == null || admin.getId().isBlank()) {
            throw new IllegalArgumentException("El administrador a actualizar requiere un ID válido.");
        }
        validarCampos(admin);

        AdministradorConector data = store.load();

        for (int i = 0; i < data.getAdministradores().size(); i++) {
            AdministradorEntity actual = data.getAdministradores().get(i);
            if (actual.getId().equals(admin.getId())) {
                data.getAdministradores().set(i, AdministradorMapper.toXml(admin));
                store.save(data);
                return admin;
            }
        }
        throw new Exception("No existe un administrador con este ID: " + admin.getId());
    }

    public boolean deleteById(String id) throws Exception {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID no puede estar vacío");
        }
        AdministradorConector data = store.load();
        boolean removed = data.getAdministradores().removeIf(x -> x.getId().equals(id));

        if (removed) {
            store.save(data);
            return true;
        } else {
            throw new Exception("No existe un administrador con este ID: " + id);
        }
    }

    // --------- Validaciones ---------

    private void validarNuevo(Administrador a) throws Exception {
        if (a == null) {
            throw new IllegalArgumentException("Administrador nulo.");
        }
        validarCampos(a);
    }

    private void validarCampos(Administrador a) throws Exception {
        if (a.getId() == null || a.getId().isBlank()) {
            throw new Exception("El ID es obligatorio.");
        }
        if (a.getClave() == null || a.getClave().isBlank()) {
            throw new Exception("La clave es obligatoria.");
        }

        if (a.getClave().length() < 4) {
            throw new Exception("La clave debe tener al menos 4 caracteres.");
        }
    }
}
