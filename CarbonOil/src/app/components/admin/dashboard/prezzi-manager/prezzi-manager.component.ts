import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Necessario per ngModel
import { DistributoreService } from '../../../../services/distributore.service';
import { Serbatoio } from '../../../../models/carburante.model';

@Component({
  selector: 'app-prezzi-manager',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './prezzi-manager.component.html',
  styleUrls: ['./prezzi-manager.component.css']
})
export class PrezziManagerComponent implements OnInit {
  serbatoi: Serbatoio[] = [];
  messaggio: string = '';

  // Hai chiamato il servizio 'fuelService' qui sotto
  constructor(private fuelService: DistributoreService) {}
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  // Assicurati che i parametri siano due: id e prezzo
salvaPrezzo(id: number, prezzo: number) {
  console.log("Tentativo di salvataggio per ID:", id, "Nuovo Prezzo:", prezzo);

  if (id != null && prezzo != null) {
    this.fuelService.aggiornaPrezzo(id, prezzo)
      .subscribe({
        next: (res) => {
          alert("Prezzo aggiornato con successo!");
          this.caricaDati(); // Ricarica la tabella per vedere i dati aggiornati
        },
        error: (err) => {
          console.error(err);
          alert("Errore durante l'invio dei dati al server.");
        }
      });
  } else {
    alert("Dati non validi: controlla i campi inseriti.");
  }
}
  caricaDati() {
    throw new Error('Method not implemented.');
  }
}