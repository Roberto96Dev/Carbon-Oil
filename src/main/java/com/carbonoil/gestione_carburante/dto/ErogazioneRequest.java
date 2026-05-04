package com.carbonoil.gestione_carburante.dto; 

public class ErogazioneRequest {
    private String tipoCarburante;
    private Double euro;

    // Getter e Setter sono VITALI qui
    public String getTipoCarburante() { return tipoCarburante; }
    public void setTipoCarburante(String tipoCarburante) { this.tipoCarburante = tipoCarburante; }
    public Double getEuro() { return euro; }
    public void setEuro(Double euro) { this.euro = euro; }
}