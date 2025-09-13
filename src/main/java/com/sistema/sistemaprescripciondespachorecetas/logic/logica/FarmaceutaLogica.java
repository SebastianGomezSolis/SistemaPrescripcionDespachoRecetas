package com.sistema.sistemaprescripciondespachorecetas.logic.logica;

import com.sistema.sistemaprescripciondespachorecetas.datos.conector.FarmaceutaConector;
import com.sistema.sistemaprescripciondespachorecetas.datos.dato.FarmaceutaDatos;
import com.sistema.sistemaprescripciondespachorecetas.datos.entity.FarmaceutaEntity;
import com.sistema.sistemaprescripciondespachorecetas.logic.Mapper.FarmaceutaMapper;
import com.sistema.sistemaprescripciondespachorecetas.model.Farmaceuta;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FarmaceutaLogica {
    private final FarmaceutaDatos store;

    // Constructor con ruta personalizada
    public FarmaceutaLogica(String rutaArchivo) {
        this.store = new FarmaceutaDatos(rutaArchivo);
    }

    // --------- Lectura ---------
    public List<Farmaceuta> findAll() {
        FarmaceutaConector data = store.load();
        return data.getFarmaceutas().stream()
                .map(FarmaceutaMapper::toModel)
                .collect(Collectors.toList());
    }

    public Optional<Farmaceuta> findByIdOptional(String id) {
        FarmaceutaConector data = store.load();
        return data.getFarmaceutas().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .map(FarmaceutaMapper::toModel);
    }

    // Método para compatibilidad con el controlador existente
    public Farmaceuta findById(String id) {
        return findByIdOptional(id).orElse(null);
    }

    public List<Farmaceuta> searchByNombre(String nombre) {
        String q = (nombre == null) ? "" : nombre.trim().toLowerCase();
        FarmaceutaConector data = store.load();
        return data.getFarmaceutas().stream()
                .filter(x -> (x.getNombre() != null && x.getNombre().toLowerCase().contains(q)) ||
                        (x.getId() != null && x.getId().toLowerCase().contains(q)))
                .map(FarmaceutaMapper::toModel)
                .collect(Collectors.toList());
    }

    // --------- Escritura ---------
    public Farmaceuta create(Farmaceuta nuevo) throws Exception {
        validarNuevo(nuevo);
        FarmaceutaConector data = store.load();

        // Verificar unicidad por ID
        boolean existeId = data.getFarmaceutas().stream()
                .anyMatch(x -> nuevo.getId().equals(x.getId()));
        if (existeId) {
            throw new Exception("Ya existe un farmaceuta con este ID: " + nuevo.getId());
        }

        // Verificar unicidad por clave (si aplica)
        if (nuevo.getClave() != null && !nuevo.getClave().isBlank()) {
            boolean existeClave = data.getFarmaceutas().stream()
                    .anyMatch(x -> nuevo.getNombre().equals(x.getNombre()));
            if (existeClave) {
                throw new Exception("Ya existe un farmaceuta con este nombre: " + nuevo.getNombre());
            }
        }

        FarmaceutaEntity entity = FarmaceutaMapper.toXml(nuevo);
        data.getFarmaceutas().add(entity);
        store.save(data);
        return FarmaceutaMapper.toModel(entity);
    }

    public Farmaceuta update(Farmaceuta farmaceuta) throws Exception {
        if (farmaceuta == null || farmaceuta.getId() == null || farmaceuta.getId().isBlank()) {
            throw new IllegalArgumentException("El farmaceuta a actualizar requiere un ID válido.");
        }

        validarCampos(farmaceuta);
        FarmaceutaConector data = store.load();

        // Validar clave duplicada (si aplica)
        if (farmaceuta.getClave() != null && !farmaceuta.getClave().isBlank()) {
            boolean existeClave = data.getFarmaceutas().stream()
                    .anyMatch(x -> farmaceuta.getNombre().equals(x.getNombre()));
            if (existeClave) {
                throw new Exception("Ya existe un farmaceuta con este nombre: " + farmaceuta.getNombre());
            }
        }

        for (int i = 0; i < data.getFarmaceutas().size(); i++) {
            FarmaceutaEntity entity = data.getFarmaceutas().get(i);
            if (entity.getId().equals(farmaceuta.getId())) {
                entity = FarmaceutaMapper.toXml(farmaceuta);
                data.getFarmaceutas().set(i, entity);
                store.save(data);
                return FarmaceutaMapper.toModel(entity);
            }
        }
        throw new Exception("No existe un farmaceuta con id: " + farmaceuta.getId());
    }

    public boolean deleteByID(String id) throws Exception {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID inválido para eliminar farmaceuta.");
        }

        FarmaceutaConector data = store.load();
        boolean removed = data.getFarmaceutas().removeIf(x -> x.getId().equals(id));

        if (removed) {
            store.save(data);
            return true;
        } else {
            throw new Exception("No existe un farmaceuta con id: " + id);
        }
    }

    // --------- Validaciones ---------
    private void validarNuevo(Farmaceuta a) throws Exception {
        if (a == null) {
            throw new IllegalArgumentException("Médico nulo.");
        }
        validarCampos(a);
    }

    private void validarCampos(Farmaceuta a) throws Exception {
        if (a.getId() == null || a.getId().isBlank()) {
            throw new Exception("El ID es obligatorio.");
        }
        if (a.getNombre() == null || a.getNombre().isBlank()) {
            throw new Exception("El nombre es obligatorio.");
        }

        // Validaciones adicionales
        if (a.getNombre().length() < 2) {
            throw new Exception("El nombre debe tener al menos 2 caracteres.");
        }
    }
}
