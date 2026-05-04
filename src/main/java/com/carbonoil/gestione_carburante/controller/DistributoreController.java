package com.carbonoil.gestione_carburante.controller;

import org.springframework.web.bind.annotation.*;

import com.carbonoil.gestione_carburante.dto.ErogazioneRequest;
import com.carbonoil.gestione_carburante.model.Serbatoio;
import com.carbonoil.gestione_carburante.model.Transazione;
import com.carbonoil.gestione_carburante.repository.LogAdminRepository;
import com.carbonoil.gestione_carburante.repository.SerbatoioRepository;
import com.carbonoil.gestione_carburante.repository.TransazioneRepository;
import com.carbonoil.gestione_carburante.service.DistributoreService;

import jakarta.transaction.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.carbonoil.gestione_carburante.model.LogAdmin;

import com.carbonoil.gestione_carburante.dto.ErogazioneResponse;




@RestController
@RequestMapping("/api/distributore")
@CrossOrigin(origins = "http://localhost:4200")
public class DistributoreController {

    @Autowired
    private SerbatoioRepository repository;

    @Autowired
    private TransazioneRepository transazioneRepository;

    @Autowired
    private DistributoreService distributoreService;

    @Autowired
    private LogAdminRepository logAdminRepository;

    @GetMapping("/serbatoi")
    public List<Serbatoio> getSerbatoi() {
        return repository.findAll();
    }

 @PostMapping("/eroga-euro")
public ResponseEntity<?> erogaPerEuro(@RequestBody ErogazioneRequest request) {
    try {
        ErogazioneResponse response = distributoreService.effettuaVenditaPerEuro(request);
        return ResponseEntity.ok(response); // Restituisce i dati al sito
   } catch (Exception e) {
        e.printStackTrace(); // Questo farà apparire l'errore nel terminale di VSCode
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(Collections.singletonMap("message", e.getMessage()));
    }
}

    @GetMapping("/transazioni/odierne")
public List<Transazione> getOdierne() {
    return distributoreService.getMovimentiOdierni();
}

    @GetMapping("/home")
    public String paginaHome(Principal principal) {
        // Principal contiene le info sull'utente loggato
        return "<h1>Accesso Eseguito!</h1>" +
               "<p>Benvenuto nel sistema CarbonOil, <b>" + principal.getName() + "</b></p>" +
               "<p>Se vedi questa pagina, la sicurezza con DB funziona correttamente.</p>";
    }

    @GetMapping("/test-libero")
public String testLibero() {
    return "Il controller funziona e Spring lo vede!";
}

public static class PrezzoRequest {
    public Long id;
    public Double prezzo;
}

@Transactional
@PatchMapping("/prezzi")
public ResponseEntity<Void> aggiornaPrezzo(@RequestBody PrezzoRequest request) {
    // Ora Spring DEVE riempire 'request'
    if (request.id == null || request.prezzo == null) {
        System.err.println("Dati mancanti! ID: " + request.id + ", Prezzo: " + request.prezzo);
        return ResponseEntity.badRequest().build();
    }

    System.out.println("Ricevuto ID: " + request.id + " con nuovo prezzo: " + request.prezzo);

    Serbatoio s = repository.findById(request.id)
            .orElseThrow(() -> new RuntimeException("Serbatoio non trovato"));

    s.setPrezzoAlLitro(request.prezzo);
    repository.save(s);

    return ResponseEntity.ok().build();
}


}
