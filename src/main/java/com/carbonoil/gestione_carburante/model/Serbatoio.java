package com.carbonoil.gestione_carburante.model;

import jakarta.persistence.*;

@Entity
@Table(name = "serbatoi")
public class Serbatoio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_carburante", unique = true)
    private String tipoCarburante; 

    private Double quantitaDisponibile;
    private Double prezzoAlLitro;
    private Double capacitaMassima;

    // Costruttore vuoto (obbligatorio per JPA)
    public Serbatoio() {}

    // AGGIUNGI QUESTO COSTRUTTORE per il DataInitializer
    public Serbatoio(String tipoCarburante, Double prezzoAlLitro, Double quantitaDisponibile) {
        this.tipoCarburante = tipoCarburante;
        this.prezzoAlLitro = prezzoAlLitro;
        this.quantitaDisponibile = quantitaDisponibile;
        this.capacitaMassima = 2000.0; // Valore di default
    }

    // Getter e Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipoCarburante() { return tipoCarburante; }
    public void setTipoCarburante(String tipoCarburante) { this.tipoCarburante = tipoCarburante; }

    public Double getPrezzoAlLitro() { return prezzoAlLitro; }
    public void setPrezzoAlLitro(Double prezzoAlLitro) { this.prezzoAlLitro = prezzoAlLitro; }

    public Double getQuantitaDisponibile() { return quantitaDisponibile; }
    public void setQuantitaDisponibile(Double quantitaDisponibile) { this.quantitaDisponibile = quantitaDisponibile; }

    public Double getCapacitaMassima() { return capacitaMassima; }
    public void setCapacitaMassima(Double capacitaMassima) { this.capacitaMassima = capacitaMassima; }
}