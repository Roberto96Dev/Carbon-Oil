package com.carbonoil.gestione_carburante.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private int status;
    private LocalDateTime timestamp;

    // DEVE esserci questo costruttore esatto:
    public ErrorResponse(String message, int status, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Aggiungi anche i Getter (o l'annotazione @Data di Lombok)
    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public LocalDateTime getTimestamp() { return timestamp; }

public void setMessage(String message) { this.message = message; }}