package com.sistema.sistemaprescripciondespachorecetas.logic.logica;

import com.sistema.sistemaprescripciondespachorecetas.datos.conector.MedicamentoConector;

import com.sistema.sistemaprescripciondespachorecetas.datos.dato.MedicamentoDatos;
import com.sistema.sistemaprescripciondespachorecetas.logic.Mapper.MedicamentoMapper;
import com.sistema.sistemaprescripciondespachorecetas.model.Medicamento;
import com.sistema.sistemaprescripciondespachorecetas.datos.entity.MedicamentoEntity;
import com.sistema.sistemaprescripciondespachorecetas.model.Medico;

import java.util.*;
import java.util.stream.Collectors;

public class MedicamentoLogica {

    private final MedicamentoDatos store;

    public MedicamentoLogica (String rutaArchivo) {
            this.store = new MedicamentoDatos(rutaArchivo);
    }
    // --------- Lectura ---------

    public List<Medicamento> findAll() {
        MedicamentoConector data = store.load();
        return data.getMedicamentos().stream()
                .map(MedicamentoMapper::toModel)
                .collect(Collectors.toList());
    }

    public Optional<Medicamento> findByIdOptional(String id) {
        MedicamentoConector data = store.load();
        return data.getMedicamentos().stream()
                .filter(x -> x.getCodigo().equals(id))
                .findFirst()
                .map(MedicamentoMapper::toModel);
    }
    // Metodo para compatibilidad con el controlador existente
    public Medicamento findById(String id) {
        return findByIdOptional(id).orElse(null);
    }


    public List<Medicamento> searchByCodigNombre(String texto) {
        String q = (texto == null) ? "" : texto.trim().toLowerCase();
        MedicamentoConector data = store.load();
        return data.getMedicamentos().stream()
                .filter(x ->
                        (x.getNombre() != null && x.getNombre().toLowerCase().contains(q)) ||
                                (x.getCodigo() != null && x.getCodigo().toLowerCase().contains(q)) ||
                                (x.getDescripcion() != null && x.getDescripcion().toLowerCase().contains(q))
                )
                .map(MedicamentoMapper::toModel)
                .collect(Collectors.toList());
    }

    // --------- Escritura ---------
    public Medicamento create(Medicamento nuevo) {
        validarNuevo(nuevo); // // Verifica campos obligatorios
        MedicamentoConector data = store.load(); //  // Carga clientes existentes

        // Unicidad por identificación (si aplica)
        if (nuevo.getCodigo() != null && !nuevo.getCodigo().isBlank()) {
            boolean existsIdent = data.getMedicamentos().stream()
                    .anyMatch(x -> nuevo.getCodigo().equalsIgnoreCase(x.getCodigo()));
            if (existsIdent) throw new IllegalArgumentException("Ya existe un medicamento con código.");
        }

        // Generar ID si viene en 0 o negativo

        if (nuevo.getCodigo().isEmpty()){
            nuevo.setCodigo(generarCodigo(nuevo));
        } else {
            boolean idTaken = data.getMedicamentos().stream().anyMatch(x -> x.getCodigo().equals(nuevo.getCodigo()));
            if (idTaken) throw new IllegalArgumentException("Ya existe un medicamento con código: " + nuevo.getCodigo());
        }


        MedicamentoEntity x = MedicamentoMapper.toXml(nuevo);
        data.getMedicamentos().add(x);
        store.save(data);
        return MedicamentoMapper.toModel(x);
    }

    public Medicamento update(Medicamento medi) {
        if (medi == null || medi.getCodigo().isEmpty())
            throw new IllegalArgumentException("El medicamento a actualizar requiere un ID válido.");
        validarCampos(medi); // Verifica que el medicamento tenga un ID válido y campos obligatorios.

        MedicamentoConector data = store.load();

        // Validar codigo duplicada (si aplica)
        if (medi.getCodigo() != null && !medi.getCodigo().isBlank()) {
            boolean conflict = data.getMedicamentos().stream()
                    .anyMatch(x -> !x.getCodigo().equals(medi.getCodigo())
                            && medi.getCodigo().equalsIgnoreCase(x.getCodigo()));
            if (conflict) throw new IllegalArgumentException("Otro medicamento ya tiene ese codigo.");
        }

        // Recorre la lista y reemplaza el medicamento que tenga el mismo codigo.
        //Si no existe, lanza excepción.
        for (int i = 0; i < data.getMedicamentos().size(); i++) {
            MedicamentoEntity actual = data.getMedicamentos().get(i);
            if (actual.getCodigo().equals(medi.getCodigo())) {
                data.getMedicamentos().set(i, MedicamentoMapper.toXml(medi));
                store.save(data);
                return medi;
            }
        }
        throw new NoSuchElementException("No existe un medicamento con código: " + medi.getCodigo());
    }

    public boolean deleteById(String id) {
        if (id == null) return false;
        MedicamentoConector data = store.load();
        boolean removed = data.getMedicamentos().removeIf(x -> x.getCodigo().equals(id));
        if (removed) store.save(data); // Guarda cambios solo si eliminó algo
        return removed;
    }


    // --------- Helpers ---------

    private void validarNuevo(Medicamento c) {
        if (c == null) throw new IllegalArgumentException("Medicamento nulo.");
        validarCampos(c);
    }

    private void validarCampos(Medicamento c) {
        if (c.getNombre() == null || c.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio.");
        if (c.getCodigo() == null || c.getCodigo().isBlank())
            throw new IllegalArgumentException("El codigo es obligatorio.");
        if (c.getDescripcion() != null && c.getDescripcion().isBlank())
            throw new IllegalArgumentException("La descripcion es obligatoria.");
    }

   private String generarCodigo(Medicamento nuevo) {
       String prefijo = nuevo.getNombre()
               .trim()
               .toUpperCase()
               .substring(0, Math.min(3, nuevo.getNombre().length()));

        return prefijo + "-" + nuevo.getDescripcion().trim();
    }




}
