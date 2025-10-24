package com.sistema.sistemaprescripciondespachorecetas.datos;

import com.sistema.sistemaprescripciondespachorecetas.model.Paciente;
import com.sistema.sistemaprescripciondespachorecetas.model.Receta;
import com.sistema.sistemaprescripciondespachorecetas.model.RecetaDetalle;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class RecetaDatos {

    public List<Receta> findAll() throws SQLException {
        String sql = "SELECT * FROM receta ORDER BY id";
        try (Connection cn = DataBase.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Receta> list = new ArrayList<>();
            PacienteDatos      pacDAO = new PacienteDatos();
            RecetaDetalleDatos detDAO = new RecetaDetalleDatos();

            Map<Integer, Paciente>      pacCache = new HashMap<>();
            Map<Integer, RecetaDetalle> detCache = new HashMap<>();

            while (rs.next()) {
                int idPac = rs.getInt("pacienteId");
                int idDet = rs.getInt("recetaDetalleId");

                Paciente p = pacCache.computeIfAbsent(idPac, k -> {
                    try { return pacDAO.findById(k); } catch (SQLException e) { throw new RuntimeException(e); }
                });
                RecetaDetalle d = detCache.computeIfAbsent(idDet, k -> {
                    try { return detDAO.findById(k); } catch (SQLException e) { throw new RuntimeException(e); }
                });

                Receta r = new Receta();
                r.setId(rs.getInt("id"));
                r.setIdentificacion(rs.getString("identificacion"));
                r.setPaciente(p);
                r.setFechaEntrega(rs.getDate("fechaEntrega").toLocalDate());
                r.setEstado(rs.getString("estado"));
                r.setMedicamento(d); // tu setter se llama en plural

                list.add(r);
            }
            return list;
        }
    }

    public Receta findById(int id) throws SQLException {
        String sql = "SELECT * FROM receta WHERE id = ?";
        try (Connection cn = DataBase.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                int idPac = rs.getInt("pacienteId");
                int idDet = rs.getInt("recetaDetalleId");

                PacienteDatos      pacDAO = new PacienteDatos();
                RecetaDetalleDatos detDAO = new RecetaDetalleDatos();

                Paciente p = pacDAO.findById(idPac);
                RecetaDetalle d = detDAO.findById(idDet);

                Receta r = new Receta();
                r.setId(rs.getInt("id"));
                r.setIdentificacion(rs.getString("identificacion"));
                r.setPaciente(p);
                r.setFechaEntrega(rs.getDate("fechaEntrega").toLocalDate());
                r.setEstado(rs.getString("estado"));
                r.setMedicamento(d);

                return r;
            }
        }
    }

    public Receta insert(Receta r) throws SQLException {
        String sql = "INSERT INTO receta (identificacion, pacienteId, fechaEntrega, recetaDetalleId, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection cn = DataBase.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, r.getIdentificacion());
            ps.setInt(2, r.getPaciente().getId());
            ps.setDate(3, Date.valueOf(r.getFechaEntrega()));
            ps.setInt(4, r.getMedicamento().getId());
            ps.setString(5, r.getEstado());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) r.setId(keys.getInt(1));
            }

            if (r.getIdentificacion() == null || r.getIdentificacion().isBlank()) {

                String SQL_NEXT = "SELECT COALESCE(MAX(CAST(SUBSTRING(identificacion, 4) AS UNSIGNED)), 0) + 1 FROM receta WHERE identificacion LIKE 'REC%'";

                int next = 1;
                try (PreparedStatement psn = cn.prepareStatement(SQL_NEXT);
                     ResultSet rs = psn.executeQuery()) {
                    if (rs.next()) next = rs.getInt(1);
                }

                String ident = "REC" + String.format("%03d", next);

                try (PreparedStatement ps2 = cn.prepareStatement(
                        "UPDATE receta SET identificacion=? WHERE id=?")) {
                    ps2.setString(1, ident);
                    ps2.setInt(2, r.getId());
                    ps2.executeUpdate();
                }

                r.setIdentificacion(ident);
            }

            return r;
        }
    }

    public Receta update(Receta r) throws SQLException {
        String sql = "UPDATE receta SET identificacion = ?, pacienteId = ?, fechaEntrega = ?, recetaDetalleId = ?, estado = ? WHERE id = ?";
        try (Connection cn = DataBase.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, r.getIdentificacion());
            ps.setInt(2, r.getPaciente().getId());
            ps.setDate(3, Date.valueOf(r.getFechaEntrega()));
            ps.setInt(4, r.getMedicamento().getId());
            ps.setString(5, r.getEstado());
            ps.setInt(6, r.getId());

            if (ps.executeUpdate() > 0) return r;
            return null;
        }
    }

    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM receta WHERE id = ?";
        try (Connection cn = DataBase.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
}
