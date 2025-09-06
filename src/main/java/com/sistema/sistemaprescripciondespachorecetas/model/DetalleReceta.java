package com.sistema.sistemaprescripciondespachorecetas.model;

public class DetalleReceta {
    private int cantidad;               // Cantidad en mg, g, ml, etc.
    private String indicaciones;        // Indicaciones adicionales para el medicamento
    private int diasDuracion;           // Dias de duracion de la receta

    public DetalleReceta() {}

    public DetalleReceta(int cantidad, String indicaciones, int diasDuracion) {
        if (indicaciones == null) { throw new IllegalArgumentException("Las indicaciones no pueden no existir."); }
        if (cantidad <= 0) { throw new IllegalArgumentException("La cantidad no puede ser menor o igual a 0."); }
        if (diasDuracion <= 0) { throw new IllegalArgumentException("La cantidad de dias de duracion no puede ser 0."); }
        this.cantidad = cantidad;
        this.indicaciones = indicaciones;
        this.diasDuracion = diasDuracion;
    }

    // Getters y Setters
    public String getIndicaciones() { return indicaciones; }
    public int getDiasDuracion() { return diasDuracion; }
    public int getCantidad() { return cantidad; }

    public void setIndicaciones(String indicaciones) { this.indicaciones = indicaciones; }
    public void setDiasDuracion(int diasDuracion) { this.diasDuracion = diasDuracion; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

}
