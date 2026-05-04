package com.carbonoil.gestione_carburante.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "transazioni")
public class Transazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID automatico (1, 2, 3...)
    private Long id;

    private String tipoCarburante;
    private Double litriErogati;
    private Double spesaEffettiva;
    private LocalDateTime dataOra; 
    private Double importo;

    // Relazione: Molte transazioni appartengono a un Serbatoio
    @ManyToOne
    @JoinColumn(name = "serbatoio_Id")
    @JsonIgnore
    private Serbatoio serbatoio;

    public Transazione() {}

    public Transazione(String tipo, Double litri, Double spesa, Serbatoio s) {
        this.tipoCarburante = tipo;
        this.litriErogati = litri;
        this.spesaEffettiva = spesa;
        this.dataOra = LocalDateTime.now();
        this.serbatoio = s;
        this.importo = spesa; // Inizialmente, importo è uguale alla spesa effettiva
    }

  // GETTER E SETTER
    public Long getId() { return id;}
     public String getTipoCarburante() { return tipoCarburante; }
    public Double getLitriErogati() {return litriErogati; }
    public Double getSpesaEffettiva() { return spesaEffettiva; }
    public LocalDateTime getDataOra() {  return dataOra;  }
    public Serbatoio getSerbatoio() {  return serbatoio; }
        public Double getImporto() { return importo; }

    public void setTipoCarburante(String tipoCarburante) {  this.tipoCarburante = tipoCarburante;  }
    public void setLitriErogati(Double litriErogati) { this.litriErogati = litriErogati; }
    public void setSpesaEffettiva(Double spesaEffettiva) {  this.spesaEffettiva = spesaEffettiva; }
    public void setDataOra(LocalDateTime dataOra) {  this.dataOra = dataOra; }
    public void setSerbatoio(Serbatoio serbatoio) { this.serbatoio = serbatoio; }
    public void setImporto(Double importo) {  this.importo = importo; }
    public void setQuantita(Double litriErogati) {  this.litriErogati = litriErogati; }

    public void aggiornaImporto(Double nuovoImporto) {
        this.importo = nuovoImporto;
    }
    
}
