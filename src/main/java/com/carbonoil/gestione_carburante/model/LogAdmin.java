package com.carbonoil.gestione_carburante.model;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity 
@Table(name = "log_admin")
@Data
public class LogAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String azione;
    
    @Column(name = "valore_precedente")
    private String valorePrecedente;
    
    @Column(name = "valore_nuovo")
    private String valoreNuovo;
    
    @Column(name = "data_ora")
    private LocalDateTime dataOra;

    @Column(name = "admin_responsabile")
    private String adminResponsabile; // L'aggiunta che abbiamo fatto per la sicurezza
}