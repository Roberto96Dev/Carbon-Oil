package com.carbonoil.gestione_carburante.controller;

import com.carbonoil.gestione_carburante.model.Transazione;
import com.carbonoil.gestione_carburante.service.DistributoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AdminController {

    private final DistributoreService distributoreService;

    /**
     * Fornisce i dati per i contatori della dashboard (totale e odierno).
     * Ora delega al Service per evitare errori 500 dovuti a valori nulli.
     */
    @GetMapping("/report/incasso")
    public Map<String, Double> getStatisticheIncasso() {
        return distributoreService.getIncassoStats();
    }

    /**
     * Fornisce la lista delle transazioni odierne per popolare la tabella.
     * Utilizza il metodo filtrato nel Service invece del findAll() generico.
     */
    @GetMapping("/report/lista")
    public List<Transazione> getListaTransazioni() {
        return distributoreService.getMovimentiOdierni();
    }

    /**
     * Aggiornamento prezzi dei carburanti.
     */
    @PutMapping("/prezzo")
    public ResponseEntity<String> aggiornaPrezzo(@RequestParam Long id, @RequestParam Double nuovoPrezzo) {
        distributoreService.aggiornaPrezzo(id, nuovoPrezzo);
        return ResponseEntity.ok("Prezzo aggiornato correttamente");
    }

    /**
     * Gestione rifornimento dei serbatoi.
     */
    @PutMapping("/rifornimento")
    public ResponseEntity<String> rifornimento(@RequestParam String tipo, @RequestParam Double litri) {
        distributoreService.rifornimento(tipo, litri);
        return ResponseEntity.ok("Rifornimento di " + tipo + " completato con successo");
    }
}