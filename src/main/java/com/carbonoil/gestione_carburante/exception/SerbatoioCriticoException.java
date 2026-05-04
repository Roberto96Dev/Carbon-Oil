package com.carbonoil.gestione_carburante.exception;

public class SerbatoioCriticoException extends RuntimeException {
    public SerbatoioCriticoException(String messaggio) {
        super(messaggio);
    }
}