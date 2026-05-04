package com.carbonoil.gestione_carburante.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ErogazioneResponse {
    private double litriErogati;
    private double resto;
    private String messaggio;
    @NotBlank(message = "Il tipo di carburante non può essere vuoto")
    private String tipoCarburante;

    @Min(value = 1, message = "l'importo minimo deve essere almeno 1")
    private double importo;

    public ErogazioneResponse(double litriErogati, double resto, String messaggio) {
        this.litriErogati = litriErogati;
        this.resto = resto;
        this.messaggio = messaggio;
    }

    public ErogazioneResponse() {
        
    }

    // Getter e Setter...
    public double getLitriErogati() { return litriErogati; }
    public String getMessaggio() { return messaggio; }
    public double getResto() { return resto; }
    public void setLitriErogati(double litriErogati) { this.litriErogati = litriErogati; }
    public void setResto(double resto) { this.resto = resto; }
    public void setMessaggio(String messaggio) { this.messaggio = messaggio; }

    public String getTipoCarburante() { return tipoCarburante; }
    public void setTipoCarburante(String tipoCarburante) { this.tipoCarburante = tipoCarburante; }
    public double getImporto() { return importo; }
    public void setImporto(double importo) { this.importo = importo; }



}