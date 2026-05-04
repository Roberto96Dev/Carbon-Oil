package com.carbonoil.gestione_carburante.repository;

import com.carbonoil.gestione_carburante.model.Serbatoio; 
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SerbatoioRepository extends JpaRepository<Serbatoio, Long> {
    Optional<Serbatoio> findByTipoCarburanteIgnoreCase(String tipoCarburante);
}