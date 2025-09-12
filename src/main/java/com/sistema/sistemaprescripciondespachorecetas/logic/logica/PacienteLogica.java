package com.sistema.sistemaprescripciondespachorecetas.logic.logica;

import com.sistema.sistemaprescripciondespachorecetas.datos.conector.MedicoConector;
import com.sistema.sistemaprescripciondespachorecetas.datos.conector.PacienteConector;
import com.sistema.sistemaprescripciondespachorecetas.datos.dato.PacienteDatos;
import com.sistema.sistemaprescripciondespachorecetas.datos.entity.MedicoEntity;
import com.sistema.sistemaprescripciondespachorecetas.datos.entity.PacienteEntity;
import com.sistema.sistemaprescripciondespachorecetas.logic.Mapper.MedicoMapper;
import com.sistema.sistemaprescripciondespachorecetas.logic.Mapper.PacienteMapper;
import com.sistema.sistemaprescripciondespachorecetas.model.Medico;
import com.sistema.sistemaprescripciondespachorecetas.model.Paciente;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PacienteLogica {
    private final PacienteDatos store;

    // Constructor con ruta personalizada
    public PacienteLogica(String rutaArchivo) {
        this.store = new PacienteDatos(rutaArchivo);
    }

    // --------- Lectura ---------
    public List<Paciente> findAll() {
        PacienteConector data = store.load();
        return data.getPacientes().stream()
                .map(PacienteMapper::toModel)
                .collect(Collectors.toList());
    }

    public Optional<Paciente> findByIdOptional(String id) {
        PacienteConector data = store.load();
        return data.getPacientes().stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .map(PacienteMapper::toModel);
    }

    // Método para compatibilidad con el controlador existente
    public Paciente findById(String id) {
        return findByIdOptional(id).orElse(null);
    }

    public List<Paciente> searchByNombre(String nombre) {
        String q = (nombre == null) ? "" : nombre.trim().toLowerCase();
        PacienteConector data = store.load();
        return data.getPacientes().stream()
                .filter(x -> (x.getNombre() != null && x.getNombre().toLowerCase().contains(q)))
                .map(PacienteMapper::toModel)
                .collect(Collectors.toList());
    }

    // --------- Escritura ---------

    public Paciente create(Paciente nuevo) throws Exception {
        validarNuevo(nuevo);
        PacienteConector data = store.load();

        // Verificar unicidad por ID
        boolean existsId = data.getPacientes().stream()
                .anyMatch(x -> nuevo.getId().equals(x.getId()));
        if (existsId) {
            throw new Exception("Ya existe un paciente con este ID: " + nuevo.getId());
        }

        // Verificar unicidad por id (si aplica)
        if (nuevo.getId() != null && !nuevo.getId().isBlank()) {
            boolean existsID = data.getPacientes().stream()
                    .anyMatch(x -> nuevo.getId().equals(x.getId()));
            if (existsID) {
                throw new Exception("Ya existe un paciente con ese Id: " + nuevo.getId());
            }
        }

        PacienteEntity entity = PacienteMapper.toXml(nuevo);
        data.getPacientes().add(entity);
        store.save(data);
        return PacienteMapper.toModel(entity);
    }

    public Paciente update(Paciente paciente) throws Exception {
        if (paciente == null || paciente.getId() == null || paciente.getId().isBlank()) {
            throw new IllegalArgumentException("El paciente a actualizar requiere un ID válido.");
        }
        validarCampos(paciente);

        PacienteConector data = store.load();

        // Validar id duplicada (si aplica)
        if (paciente.getId() != null && !paciente.getId().isBlank()) {
            boolean conflict = data.getPacientes().stream()
                    .anyMatch(x -> x.getId().equals(paciente.getId()));
            if (conflict) {
                throw new Exception("Otro paciente ya tiene ese id: " + paciente.getId());
            }
        }

        for (int i = 0; i < data.getPacientes().size(); i++) {
            PacienteEntity actual = data.getPacientes().get(i);
            if (actual.getId().equals(paciente.getId())) {
                data.getPacientes().set(i, PacienteMapper.toXml(paciente));
                store.save(data);
                return paciente;
            }
        }
        throw new Exception("No existe un médico con este ID: " + paciente.getId());
    }

    public boolean deleteById(String id) throws Exception {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El ID no puede estar vacío");
        }

        PacienteConector data = store.load();
        boolean removed = data.getPacientes().removeIf(x -> x.getId().equals(id));

        if (removed) {
            store.save(data);
            return true;
        } else {
            throw new Exception("No existe un médico con este ID: " + id);
        }
    }


    // --------- Validaciones ---------

    private void validarNuevo(Paciente p) throws Exception {
        if (p == null) {
            throw new IllegalArgumentException("Médico nulo.");
        }
        validarCampos(p);
    }

    private void validarCampos(Paciente p) throws Exception {
        if (p.getId() == null || p.getId().isBlank()) {
            throw new Exception("El ID es obligatorio.");
        }
        if (p.getNombre() == null || p.getNombre().isBlank()) {
            throw new Exception("El nombre es obligatorio.");
        }
        if (p.getTelefono() == null || p.getTelefono().isBlank()) {
            throw new Exception("La especialidad es obligatoria.");
        }

        // Validaciones adicionales
        if (p.getNombre().length() < 2) {
            throw new Exception("El nombre debe tener al menos 2 caracteres.");
        }
        if (p.getTelefono().length() < 3) {
            throw new Exception("La especialidad debe tener al menos 3 caracteres.");
        }
    }
}
