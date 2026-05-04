package com.carbonoil.gestione_carburante.repository;

import com.carbonoil.gestione_carburante.model.LogAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogAdminRepository extends JpaRepository<LogAdmin, Long> {
}