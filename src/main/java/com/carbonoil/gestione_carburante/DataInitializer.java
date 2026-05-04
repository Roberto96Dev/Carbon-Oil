package com.carbonoil.gestione_carburante;




import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.carbonoil.gestione_carburante.model.Serbatoio;
import com.carbonoil.gestione_carburante.model.Utente;
import com.carbonoil.gestione_carburante.repository.SerbatoioRepository;
import com.carbonoil.gestione_carburante.repository.UtenteRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UtenteRepository utenteRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SerbatoioRepository repository;

    // Un unico costruttore per tutte le dipendenze (Best Practice)
    public DataInitializer(UtenteRepository utenteRepository, 
                           BCryptPasswordEncoder passwordEncoder, 
                           SerbatoioRepository repository) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Popolamento Serbatoi
        if (repository.count() == 0) {
            // ATTENZIONE: Se il costruttore di Serbatoio dà errore, 
            // controlla che esista nel modello Serbatoio.java
            repository.save(new Serbatoio("BENZINA", 1.728, 1000.0));
            repository.save(new Serbatoio("DIESEL", 1.859, 1000.0));
            repository.save(new Serbatoio("GPL", 0.757, 500.0));
            repository.save(new Serbatoio("METANO", 0.988, 500.0));
            System.out.println("Database CarbonOil popolato!");
        }

        // Creazione Admin
       if (utenteRepository.findByUsername("admin").isEmpty()) {
    Utente admin = new Utente();
    admin.setUsername("admin");
    admin.setPassword(passwordEncoder.encode("admin123")); 
    admin.setRuolo("ROLE_ADMIN"); // <--- Fondamentale aggiungere ROLE_
    
    utenteRepository.save(admin);
    System.out.println("Utente Admin creato correttamente con ROLE_ADMIN!");
}
    }
}