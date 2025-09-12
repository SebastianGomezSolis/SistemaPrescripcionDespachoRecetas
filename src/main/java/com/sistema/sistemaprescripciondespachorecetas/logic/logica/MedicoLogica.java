package com.sistema.sistemaprescripciondespachorecetas.logic.logica;

import com.sistema.sistemaprescripciondespachorecetas.datos.conector.MedicoConector;
import com.sistema.sistemaprescripciondespachorecetas.datos.dato.MedicoDatos;
import com.sistema.sistemaprescripciondespachorecetas.datos.entity.MedicoEntity;
import com.sistema.sistemaprescripciondespachorecetas.logic.Mapper.MedicoMapper;
import com.sistema.sistemaprescripciondespachorecetas.model.Medico;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/** CRUD para Medico usando un archivo XML como almacenamiento. */
public class MedicoLogica {

    private final MedicoDatos store;

    /** Constructor con ruta personalizada */
    public MedicoLogica(String rutaArchivo) {
        this.store = new MedicoDatos(rutaArchivo);
    }


    // --------- Lectura ---------

    public List<Medico> findAll() {
        MedicoConector data = store.load();
        return data.getMedicos().stream()
                .map(MedicoMapper::toModel)
                .collect(Collectors.toList());
    }

    public Optional<Medico> findByIdOptional(String id) {
        MedicoConector data = store.load();
        return data.getMedicos().stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .map(MedicoMapper::toModel);
    }

    // Método para compatibilidad con el controlador existente
    public Medico findById(String id) {
        return findByIdOptional(id).orElse(null);
    }

    public List<Medico> searchByNombreOEspecialidad(String texto) {
        String q = (texto == null) ? "" : texto.trim().toLowerCase();
        MedicoConector data = store.load();
        return data.getMedicos().stream()
                .filter(x ->
                        (x.getNombre() != null && x.getNombre().toLowerCase().contains(q)) ||
                                (x.getEspecialidad() != null && x.getEspecialidad().toLowerCase().contains(q)) ||
                                (x.getId() != null && x.getId().toLowerCase().contains(q))
                )
                .map(MedicoMapper::toModel)
                .collect(Collectors.toList());
    }

    public List<Medico> findByEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.isBlank()) {
            return findAll();
        }

        MedicoConector data = store.load();
        return data.getMedicos().stream()
                .filter(x -> x.getEspecialidad() != null &&
                        x.getEspecialidad().toLowerCase().contains(especialidad.toLowerCase()))
                .map(MedicoMapper::toModel)
                .collect(Collectors.toList());
    }

    // --------- Escritura ---------

    public Medico create(Medico nuevo) throws Exception {
        validarNuevo(nuevo);
        MedicoConector data = store.load();

        // Verificar unicidad por ID
        boolean existsId = data.getMedicos().stream()
                .anyMatch(x -> nuevo.getId().equals(x.getId()));
        if (existsId) {
            throw new Exception("Ya existe un médico con este ID: " + nuevo.getId());
        }

        // Verificar unicidad por clave (si aplica)
        if (nuevo.getClave() != null && !nuevo.getClave().isBlank()) {
            boolean existsClave = data.getMedicos().stream()
                    .anyMatch(x -> nuevo.getClave().equals(x.getClave()));
            if (existsClave) {
                throw new Exception("Ya existe un médico con esta clave: " + nuevo.getClave());
            }
        }

        MedicoEntity entity = MedicoMapper.toXml(nuevo);
        data.getMedicos().add(entity);
        store.save(data);
        return MedicoMapper.toModel(entity);
    }

    public Medico update(Medico medico) throws Exception {
        if (medico == null || medico.getId() == null || medico.getId().isBlank()) {
            throw new IllegalArgumentException("El médico a actualizar requiere un ID válido.");
        }
        validarCampos(medico);

        MedicoConector data = store.load();

        // Validar clave duplicada (si aplica)
        if (medico.getClave() != null && !medico.getClave().isBlank()) {
            boolean conflict = data.getMedicos().stream()
                    .anyMatch(x -> !x.getId().equals(medico.getId()) &&
                            medico.getClave().equals(x.getClave()));
            if (conflict) {
                throw new Exception("Otro médico ya tiene esa clave: " + medico.getClave());
            }
        }

        for (int i = 0; i < data.getMedicos().size(); i++) {
            MedicoEntity actual = data.getMedicos().get(i);
            if (actual.getId().equals(medico.getId())) {
                data.getMedicos().set(i, MedicoMapper.toXml(medico));
                store.save(data);
                return medico;
            }
        }
        throw new Exception("No existe un médico con este ID: " + medico.getId());
    }

    public boolean deleteById(String id) throws Exception {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID no puede estar vacío");
        }

        MedicoConector data = store.load();
        boolean removed = data.getMedicos().removeIf(x -> x.getId().equals(id));

        if (removed) {
            store.save(data);
            return true;
        } else {
            throw new Exception("No existe un médico con este ID: " + id);
        }
    }


    // --------- Validaciones ---------

    private void validarNuevo(Medico m) throws Exception {
        if (m == null) {
            throw new IllegalArgumentException("Médico nulo.");
        }
        validarCampos(m);
    }

    private void validarCampos(Medico m) throws Exception {
        if (m.getId() == null || m.getId().isBlank()) {
            throw new Exception("El ID es obligatorio.");
        }
        if (m.getNombre() == null || m.getNombre().isBlank()) {
            throw new Exception("El nombre es obligatorio.");
        }
        if (m.getEspecialidad() == null || m.getEspecialidad().isBlank()) {
            throw new Exception("La especialidad es obligatoria.");
        }

        // Validaciones adicionales
        if (m.getNombre().length() < 2) {
            throw new Exception("El nombre debe tener al menos 2 caracteres.");
        }
        if (m.getEspecialidad().length() < 3) {
            throw new Exception("La especialidad debe tener al menos 3 caracteres.");
        }
    }
}