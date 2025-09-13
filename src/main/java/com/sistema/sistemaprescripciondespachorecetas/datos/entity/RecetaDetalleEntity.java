package com.sistema.sistemaprescripciondespachorecetas.datos.entity;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;

@XmlAccessorType(XmlAccessType.FIELD)
public class RecetaDetalleEntity {

    private MedicamentoEntity medicamento;
    private int cantidad;
    private String indicaciones;
    private int diasDuracion;

    public MedicamentoEntity getMedicamento() { return medicamento; }
    public void setMedicamento(MedicamentoEntity medicamento) { this.medicamento = medicamento; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getIndicaciones() { return indicaciones; }
    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }

    public int getDiasDuracion() { return diasDuracion; }
    public void setDiasDuracion(int diasDuracion) { this.diasDuracion = diasDuracion; }
}

