package com.carbonoil.gestione_carburante.model;


import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;



@Entity
@Table(name = "utenti")
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String ruolo;
    // Costruttori, getter e setter 
    public Utente() {
    }

    public Utente(String username, String password, String ruolo) {
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    // Getter e setter
    public Long getId() {  return id; }

    public void setId(Long id) {   this.id = id; }

    public String getUsername() {  return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getRuolo() { return ruolo; }

    public void setRuolo(String ruolo) {  this.ruolo = ruolo; }
}
