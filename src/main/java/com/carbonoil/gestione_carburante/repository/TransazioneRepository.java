package com.carbonoil.gestione_carburante.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.carbonoil.gestione_carburante.model.Transazione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;

@Repository
public interface TransazioneRepository extends JpaRepository<Transazione, Long> {
    
    //Query esistente: Incasso totale di sempre
    @Query("SELECT SUM(t.importo) FROM Transazione t")
    Double calcolaIncassoTotale();

    // Nuova Query: Incasso solo dalle 00:00 di oggi ad ora
    // Usiamo CURRENT_DATE per identificare l'inizio del giorno corrente
  @Query("SELECT SUM(t.importo) FROM Transazione t WHERE t.dataOra >= :inizioGiorno")
Double calcolaIncassoGiornaliero(@Param("inizioGiorno") LocalDateTime inizioGiorno);

    // Ti servirà anche questa per la lista dei movimenti in tabella!
    @Query("SELECT t FROM Transazione t WHERE t.dataOra >= :inizioGiorno ORDER BY t.dataOra DESC")
    List<Transazione> trovaTransazioniOdierne(@Param("inizioGiorno") LocalDateTime inizioGiorno);
}
