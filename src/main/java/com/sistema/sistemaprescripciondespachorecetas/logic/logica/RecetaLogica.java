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

    public RecetaLogica (String rutaArchivo) {
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
                .filter(x ->  x.getId().equals(id))
                .findFirst()
                .map(RecetaMapper::toModel);
    }


    // --------- Escritura ---------
    public Receta create(Receta nuevo) {
        validarNuevo(nuevo); // // Verifica campos obligatorios
        RecetaConector data = store.load(); //  // Carga clientes existentes


        // Generar ID si viene en 0 o negativo
        if (nuevo.getId() != null && !nuevo.getId().isBlank()){
            nuevo.setId(generarSiguienteId(data));
        } else {
            boolean idTaken = data.getRecetas().stream().anyMatch(x -> x.getId().equals(nuevo.getId()));
            if (idTaken) throw new IllegalArgumentException("Ya existe una receta con id: " + nuevo.getId());
        }


        RecetaEntity x = RecetaMapper.toXml(nuevo);
        data.getRecetas().add(x);
        store.save(data);
        return RecetaMapper.toModel(x);
    }

    public Receta update(Receta receta) {
        if (receta == null || receta.getId().isEmpty())
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
        if (removed) store.save(data); // Guarda cambios solo si eliminó algo
        return removed;
    }


    // --------- Helpers ---------

    private void validarNuevo(Receta c) {
        if (c == null) throw new IllegalArgumentException("Receta nula.");
        validarCampos(c);
    }

    private void validarCampos(Receta r) {
        if (r.getPaciente() == null) {
            throw new IllegalArgumentException("El paciente es obligatorio.");
        }
        if (r.getMedicamento() == null) {
            throw new IllegalArgumentException("El medicamento es obligatorio.");
        }
        if (r.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        if (r.getDiasDuracion() <= 0) {
            throw new IllegalArgumentException("Los días de duración deben ser mayores a cero.");
        }
        if (r.getIndicaciones() == null || r.getIndicaciones().isBlank()) {
            throw new IllegalArgumentException("Las indicaciones son obligatorias.");
        }
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

