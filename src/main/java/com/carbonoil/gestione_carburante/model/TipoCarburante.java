package com.carbonoil.gestione_carburante.model;

import jakarta.persistence.*;

// Se vuoi che sia una tabella, deve essere così:
@Entity
@Table(name = "tipi_carburante")
public class TipoCarburante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nome;

    // Costruttore vuoto obbligatorio
    public TipoCarburante() {}

    public TipoCarburante(String nome) {
        this.nome = nome;
    }

    // GETTER E SETTER (Assicurati che ci siano tutti)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}