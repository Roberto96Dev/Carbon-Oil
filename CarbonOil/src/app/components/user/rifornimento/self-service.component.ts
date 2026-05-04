import { Component, OnInit, ChangeDetectorRef, AfterViewInit } from '@angular/core'; // 1. Aggiunto ChangeDetectorRef
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DistributoreService } from '../../../services/distributore.service'; 
import { Serbatoio, ErogazioneResponse } from '../../../models/carburante.model';



@Component({
  selector: 'app-self-service',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './self-service.component.html',
  styleUrl: './self-service.component.css'
})
export class SelfServiceComponent implements OnInit, AfterViewInit {
  serbatoi: any[] = [];
  tipoSelezionato: string = '';
  importoEuro: number | undefined = undefined;
  messaggioRisposta: string = '';
  erogazioneRiuscita: boolean = false; 
  litriErogati: number = 0;
  tipoErogato: string = '';
  importoErogato: number = 0;

  // 2. Iniettiamo ChangeDetectorRef nel costruttore
  constructor(
    private api: DistributoreService,
    private cdr: ChangeDetectorRef 
  ) {}


  ngAfterViewInit() {
    this.cdr.detectChanges(); // Forza il controllo dopo che la vista è pronta
}

  ngOnInit(): void {
    this.caricaDati();
  }

  // Metodo separato per caricare i dati e forzare il refresh della vista
  caricaDati(): void {
    this.api.getSerbatoi().subscribe({
      next: (data) => {
        this.serbatoi = data;
        // 3. Questo forza Angular a mostrare i dati subito al refresh
        this.cdr.detectChanges(); 
      },
      error: (err) => console.error("Errore caricamento serbatoi:", err)
    });
  }

  seleziona(tipo: string): void {
    this.tipoSelezionato = tipo;
    this.messaggioRisposta = '';
  }

  eseguiPagamento(): void {
    if (!this.tipoSelezionato || !this.importoEuro || this.importoEuro <= 0) {
      alert("Seleziona un carburante e inserisci un importo valido.");
      return;
    }

    const richiesta = { 
      tipoCarburante: this.tipoSelezionato, 
      euro: this.importoEuro 
    };

    this.api.erogaEuro(richiesta).subscribe({
      next: (res: any) => {
        this.erogazioneRiuscita = true;
        this.litriErogati = res.litriErogati; 
        this.tipoErogato = res.tipoCarburante;

        this.importoErogato = res.importo;

        this.messaggioRisposta = "OK";
        this.cdr.detectChanges();
        
        // Reset dell'input
        this.importoEuro = undefined;
        
        // Aggiorna i livelli dei serbatoi e forza il refresh
        this.caricaDati();

        // (Opzionale) Nascondi il messaggio di successo dopo 5 secondi
        setTimeout(() => {
          this.messaggioRisposta = '';
          this.cdr.detectChanges();
        }, 5000);
      },
      error: (err: any) => {
        this.erogazioneRiuscita = false;
        this.messaggioRisposta = "Errore durante l'erogazione.";
        this.cdr.detectChanges();
      }
    });
  }
}