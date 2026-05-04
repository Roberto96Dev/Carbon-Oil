package com.carbonoil.gestione_carburante.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.carbonoil.gestione_carburante.model.Utente;
import com.carbonoil.gestione_carburante.repository.UtenteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(utente.getUsername())
                .password(utente.getPassword()) // Questa è la password già criptata nel DB
                .roles(utente.getRuolo()) // Es: ADMIN
                .build();
    }
}