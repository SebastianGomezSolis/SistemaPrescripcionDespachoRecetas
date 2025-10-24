package com.sistema.sistemaprescripciondespachorecetas.logic;

import com.sistema.sistemaprescripciondespachorecetas.datos.PacienteDatos;
import com.sistema.sistemaprescripciondespachorecetas.datos.RecetaDatos;
import com.sistema.sistemaprescripciondespachorecetas.datos.RecetaDetalleDatos;
import com.sistema.sistemaprescripciondespachorecetas.model.Paciente;
import com.sistema.sistemaprescripciondespachorecetas.model.Receta;
import com.sistema.sistemaprescripciondespachorecetas.model.RecetaDetalle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecetaLogica {

    private final RecetaDatos store = new RecetaDatos();
    private final PacienteDatos pacStore = new PacienteDatos();
    private final RecetaDetalleDatos detStore = new RecetaDetalleDatos();

    // --------- Lectura ---------
    public List<Receta> findAll() throws SQLException {
        return store.findAll();
    }

    public Receta findById(int id) throws SQLException {
        return store.findById(id);
    }

    // --------- Escritura ---------
    public Receta create(Receta nueva) throws SQLException {
        validarNueva(nueva);

        Paciente p = pacStore.findById(nueva.getPaciente().getId());
        if (p == null)
            throw new IllegalArgumentException("El paciente no existe (id=" + nueva.getPaciente().getId() + ").");

        RecetaDetalle d = nueva.getMedicamento();
        if (d.getId() == 0) {
            d = detStore.insert(d);
            nueva.setMedicamento(d);
        } else {
            if (detStore.findById(d.getId()) == null)
                throw new IllegalArgumentException("El detalle de receta no existe (id=" + d.getId() + ").");
        }

        return store.insert(nueva);
    }


    public Receta update(Receta r) throws SQLException {
        if (r == null || r.getId() <= 0)
            throw new IllegalArgumentException("La receta a actualizar requiere un ID válido.");

        validarCampos(r);

        if (pacStore.findById(r.getPaciente().getId()) == null)
            throw new IllegalArgumentException("El paciente no existe (id=" + r.getPaciente().getId() + ").");
        if (detStore.findById(r.getMedicamento().getId()) == null)
            throw new IllegalArgumentException("El detalle de receta no existe (id=" + r.getMedicamento().getId() + ").");

        return store.update(r);
    }

    public boolean deleteById(int id) throws SQLException {
        if (id <= 0) return false;
        return store.delete(id) > 0;
    }

    // --------- Helpers ---------
    private void validarNueva(Receta r) {
        if (r == null) throw new IllegalArgumentException("Receta nula.");
        validarCampos(r);
    }

    private void validarCampos(Receta r) {
        if (r.getPaciente() == null) throw new IllegalArgumentException("El paciente es obligatorio.");
        if (r.getMedicamento() == null) throw new IllegalArgumentException("El detalle de receta es obligatorio.");
        var d = r.getMedicamento();
        if (d.getMedicamento() == null) throw new IllegalArgumentException("Debe seleccionar un medicamento.");
        if (d.getCantidad() <= 0) throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        if (d.getDiasDuracion() <= 0) throw new IllegalArgumentException("Los días de duración deben ser mayores a cero.");
        if (d.getIndicaciones() == null || d.getIndicaciones().isBlank()) throw new IllegalArgumentException("Las indicaciones son obligatorias.");

    }
}
