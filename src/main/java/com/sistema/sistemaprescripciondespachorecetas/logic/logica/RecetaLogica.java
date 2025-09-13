package com.sistema.sistemaprescripciondespachorecetas.logic.logica;

import com.sistema.sistemaprescripciondespachorecetas.datos.conector.RecetaConector;
import com.sistema.sistemaprescripciondespachorecetas.datos.dato.RecetaDatos;
import com.sistema.sistemaprescripciondespachorecetas.logic.Mapper.RecetaMapper;
import com.sistema.sistemaprescripciondespachorecetas.model.Receta;
import com.sistema.sistemaprescripciondespachorecetas.datos.entity.RecetaEntity;

import java.util.*;
import java.util.stream.Collectors;

public class RecetaLogica {

    private final RecetaDatos store;

    public RecetaLogica(String rutaArchivo) {
        this.store = new RecetaDatos(rutaArchivo);
    }

    // --------- Lectura ---------
    public List<Receta> findAll() {
        RecetaConector data = store.load();
        return data.getRecetas().stream()
                .map(RecetaMapper::toModel)
                .collect(Collectors.toList());
    }

    public Optional<Receta> findById(String id) {
        RecetaConector data = store.load();
        return data.getRecetas().stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .map(RecetaMapper::toModel);
    }

    // --------- Escritura ---------
    public Receta create(Receta nuevo) {
        validarNuevo(nuevo);
        RecetaConector data = store.load();

        // Generar ID si no tiene o está en blanco
        if (nuevo.getId() == null || nuevo.getId().isBlank()) {
            nuevo.setId(generarSiguienteId(data));
        } else {
            boolean idTaken = data.getRecetas().stream()
                    .anyMatch(x -> x.getId().equals(nuevo.getId()));
            if (idTaken) {
                throw new IllegalArgumentException("Ya existe una receta con id: " + nuevo.getId());
            }
        }

        RecetaEntity x = RecetaMapper.toXml(nuevo);
        data.getRecetas().add(x);
        store.save(data);
        return RecetaMapper.toModel(x);
    }

    public Receta update(Receta receta) {
        if (receta == null || receta.getId() == null || receta.getId().isBlank())
            throw new IllegalArgumentException("La receta a actualizar requiere un ID válido.");
        validarCampos(receta);

        RecetaConector data = store.load();

        for (int i = 0; i < data.getRecetas().size(); i++) {
            RecetaEntity actual = data.getRecetas().get(i);
            if (actual.getId().equals(receta.getId())) {
                data.getRecetas().set(i, RecetaMapper.toXml(receta));
                store.save(data);
                return receta;
            }
        }
        throw new NoSuchElementException("No existe una receta con id: " + receta.getId());
    }

    public boolean deleteById(String id) {
        if (id == null) return false;
        RecetaConector data = store.load();
        boolean removed = data.getRecetas().removeIf(x -> x.getId().equals(id));
        if (removed) store.save(data);
        return removed;
    }

    // --------- Helpers ---------
    private void validarNuevo(Receta receta) {
        if (receta == null)
            throw new IllegalArgumentException("Receta nula.");
        validarCampos(receta);
    }

    private void validarCampos(Receta receta) {
        if (receta.getPaciente() == null) {
            throw new IllegalArgumentException("El paciente es obligatorio.");
        }
        if (receta.getMedicamentos() == null || receta.getMedicamentos().isEmpty()) {
            throw new IllegalArgumentException("La receta debe contener al menos un medicamento.");
        }
        // Validar cada detalle de receta
        receta.getMedicamentos().forEach(detalle -> {
            if (detalle.getMedicamento() == null) {
                throw new IllegalArgumentException("Cada detalle debe tener un medicamento.");
            }
            if (detalle.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
            }
            if (detalle.getDiasDuracion() <= 0) {
                throw new IllegalArgumentException("Los días de duración deben ser mayores a cero.");
            }
            if (detalle.getIndicaciones() == null || detalle.getIndicaciones().isBlank()) {
                throw new IllegalArgumentException("Las indicaciones son obligatorias.");
            }
        });
    }

    private String generarSiguienteId(RecetaConector data) {
        int max = data.getRecetas().stream()
                .map(RecetaEntity::getId)
                .filter(Objects::nonNull)
                .filter(id -> id.matches("REC\\d+"))
                .map(id -> id.replace("REC", ""))
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(0);

        int siguiente = max + 1;

        return String.format("REC%03d", siguiente);
    }
}
