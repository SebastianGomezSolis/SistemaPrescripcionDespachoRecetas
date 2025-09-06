package com.sistema.sistemaprescripciondespachorecetas.datos.conector;

import com.sistema.sistemaprescripciondespachorecetas.datos.entity.MedicamentoEntity;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "medicamentosData")
@XmlAccessorType(XmlAccessType.FIELD)
public class MedicamentoConector {
    @XmlElementWrapper(name = "medicamentos")
    @XmlElement(name = "medicamento")
    private List<MedicamentoEntity> medicamentos = new ArrayList<>();

    // Getters y Setters
    public List<MedicamentoEntity> getMedicamentos() { return medicamentos; }
    public void setMedicamentos(List<MedicamentoEntity> medicamentos) { this.medicamentos = medicamentos; }

}
