package com.sistema.sistemaprescripciondespachorecetas.datos.conector;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.PacienteEntity;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "pacientesData")
@XmlAccessorType(XmlAccessType.FIELD)
public class PacienteConector {
    @XmlElementWrapper(name = "pacientes")
    @XmlElement(name = "paciente")
    private List<PacienteEntity> pacientes = new ArrayList<>();

    // Getters y Setters
    public List<PacienteEntity> getPacientes() { return pacientes; }
    public void setPacientes(List<PacienteEntity> pacientes) { this.pacientes = pacientes; }

}
