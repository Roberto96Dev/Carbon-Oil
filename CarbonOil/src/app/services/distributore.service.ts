import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Serbatoio, ErogazioneRequest, ErogazioneResponse } from '../models/carburante.model';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DistributoreService {
  [x: string]: any;
  // Il tuo backend Spring Boot risponde sulla porta 8085
  private apiUrl = 'http://localhost:8085/api/distributore';
  private readonly API_ADMIN = 'http://localhost:8085/api/admin';

  constructor(private http: HttpClient) { }

  // --- METODI PER L'UTENTE (Self-Service) ---

  /** Recupera la lista di tutti i serbatoi e i prezzi attuali */
 getSerbatoi(): Observable<Serbatoio[]> {
  // Assicurati che questo URL sia IDENTICO a quello che funziona nel browser
return this.http.get<Serbatoio[]>(`${this.apiUrl}/serbatoi`);
;}

  /** Invia la richiesta di erogazione in base all'importo in Euro */
  erogaEuro(request: ErogazioneRequest): Observable<ErogazioneResponse> {
    return this.http.post<ErogazioneResponse>(`${this.apiUrl}/eroga-euro`, request);
  }

  // --- METODI PER L'AMMINISTRATORE (Dashboard & Prezzi) ---
getReport(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/transazioni/odierne`);
  }
  /** Recupera l'incasso totale accumulato dal distributore */
  getIncassoStats(): Observable<any> {
  return this.http.get<any>(`${this.API_ADMIN}/report/incasso`);
}

  /** 
   * Aggiorna il prezzo di un carburante. 
   * Nota: usa l'ID come richiesto dal tuo PrezziManagerComponent
   */
  
  aggiornaPrezzo(id: number, prezzo: number): Observable<any> {
  // Questo crea l'oggetto {id: 1, prezzo: 1.75}
  const body = { id, prezzo }; 
  
  console.log("Il service sta inviando questo al server:", body);
  
  return this.http.patch(`${this.apiUrl}/prezzi`, body);
}
  /** 
   * Esegue il rifornimento fisico di un serbatoio
   * Il backend usa @RequestParam, quindi i dati vanno nei parametri dell'URL
   */
  rifornimento(tipo: string, litri: number): Observable<string> {
    return this.http.put(`${this.API_ADMIN}/rifornimento`, null, {
      params: { tipo, litri },
      responseType: 'text'
    });
  }
}