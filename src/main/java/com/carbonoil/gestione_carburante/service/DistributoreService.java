package com.carbonoil.gestione_carburante.service;

import com.carbonoil.gestione_carburante.dto.ErogazioneRequest;
import com.carbonoil.gestione_carburante.dto.ErogazioneResponse;
import com.carbonoil.gestione_carburante.exception.SerbatoioCriticoException;
import com.carbonoil.gestione_carburante.model.Serbatoio;
import com.carbonoil.gestione_carburante.model.Transazione;
import com.carbonoil.gestione_carburante.repository.SerbatoioRepository;
import com.carbonoil.gestione_carburante.repository.TransazioneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DistributoreService {

    private final SerbatoioRepository serbatoioRepository;
    private final TransazioneRepository transazioneRepository;

    private static final double SOGLIA_BLOCCO = 10.0;
    private static final double SOGLIA_ALERT = 50.0;

    /**
     * Recupera le statistiche di incasso (Totale e Odierno) per la dashboard.
     * Gestisce i valori null per evitare l'Errore 500.
     */
    public Map<String, Double> getIncassoStats() {
        LocalDateTime inizioGiorno = LocalDate.now().atStartOfDay();
        
        Double totale = transazioneRepository.calcolaIncassoTotale();
        Double odierno = transazioneRepository.calcolaIncassoGiornaliero(inizioGiorno);

        Map<String, Double> stats = new HashMap<>();
        stats.put("totale", totale != null ? totale : 0.0);
        stats.put("odierno", odierno != null ? odierno : 0.0);
        return stats;
    }

    /**
     * Recupera la lista delle transazioni effettuate oggi.
     */
    public List<Transazione> getMovimentiOdierni() {
        try {
            LocalDateTime oggiMezzanotte = LocalDate.now().atStartOfDay();
            List<Transazione> lista = transazioneRepository.trovaTransazioniOdierne(oggiMezzanotte);
            return (lista != null) ? lista : new ArrayList<>();
        } catch (Exception e) {
            log.error("Errore nel recupero dei movimenti odierni: ", e);
            return new ArrayList<>();
        }
    }

    @Transactional
    public ErogazioneResponse effettuaVenditaPerEuro(ErogazioneRequest request) {
        Serbatoio s = serbatoioRepository.findByTipoCarburanteIgnoreCase(request.getTipoCarburante())
                .orElseThrow(() -> new RuntimeException("Serbatoio non trovato: " + request.getTipoCarburante()));

        if (s.getQuantitaDisponibile() <= SOGLIA_BLOCCO) {
            throw new SerbatoioCriticoException("Erogatore fuori servizio: carburante insufficiente");
        }

        double litriRichiesti = request.getEuro() / s.getPrezzoAlLitro();
        double quantitaUtilizzabile = s.getQuantitaDisponibile() - SOGLIA_BLOCCO;
        
        double litriErogati = Math.min(litriRichiesti, quantitaUtilizzabile);
        double spesaReale = litriErogati * s.getPrezzoAlLitro();
        
        String messaggio = (litriRichiesti > quantitaUtilizzabile) ? 
                "Erogazione parziale: serbatoio esaurito." : "Erogazione completata con successo.";

        // Aggiornamento Stato Serbatoio
        s.setQuantitaDisponibile(s.getQuantitaDisponibile() - litriErogati);
        serbatoioRepository.save(s);

        // Registrazione Transazione
        registraTransazione(s, litriErogati, spesaReale);

        if (s.getQuantitaDisponibile() < SOGLIA_ALERT) {
            log.warn("ALERT CRITICO: {} sotto soglia minima!", s.getTipoCarburante());
        }

        return buildResponse(s, litriErogati, spesaReale, messaggio);
    }

    private void registraTransazione(Serbatoio s, double litri, double importo) {
        Transazione t = new Transazione();
        t.setTipoCarburante(s.getTipoCarburante());
        t.setLitriErogati(litri);
        t.setImporto(importo); 
        t.setDataOra(LocalDateTime.now());
        transazioneRepository.save(t);
    }

    @Transactional
    public void aggiornaPrezzo(Long id, Double nuovoPrezzo) {
        Serbatoio s = serbatoioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serbatoio non trovato"));
        s.setPrezzoAlLitro(nuovoPrezzo);
        serbatoioRepository.save(s);
    }

    @Transactional
    public void rifornimento(String tipo, Double litriAggiunti) {
        Serbatoio s = serbatoioRepository.findByTipoCarburanteIgnoreCase(tipo)
                .orElseThrow(() -> new RuntimeException("Tipo carburante non valido"));
        s.setQuantitaDisponibile(s.getQuantitaDisponibile() + litriAggiunti);
        serbatoioRepository.save(s);
    }

    public List<Serbatoio> getTuttiSerbatoi() {
        return serbatoioRepository.findAll();
    }

    private ErogazioneResponse buildResponse(Serbatoio s, double litri, double importo, String msg) {
        ErogazioneResponse res = new ErogazioneResponse();
        res.setLitriErogati(litri);
        res.setImporto(importo);
        res.setMessaggio(msg);
        res.setTipoCarburante(s.getTipoCarburante());
        return res;
    }
}