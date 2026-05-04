import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DistributoreService } from '../../../services/distributore.service'; 
import { Serbatoio } from '../../../models/carburante.model';

interface IncassoStats {
  totale: number;
  odierno: number;
}

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  // Dati
  serbatoi: Serbatoio[] = [];
  incassoTotale: number = 0;
  incassoOggi: number = 0;
  transazioni: any[] = []; 
  today: Date = new Date();
  private intervalId: any;
  // Stato Sicurezza
  isAdmin: boolean = false;
  passwordInserita: string = '';
  
  // Helper Maps per gli input
  nuoviPrezzi: { [key: string]: number } = {};
  litriRifornimento: { [key: string]: number } = {};

  constructor(private api: DistributoreService) {}

  ngOnInit() {
  if (sessionStorage.getItem('adminAuth') === 'true') {
    this.isAdmin = true;
    // Usiamo setTimeout per evitare l'errore NG0100
    setTimeout(() => {
      this.caricaDatiIniziali();
      this.intervalId = setInterval(() => {
    if (this.isAdmin) this.caricaDatiIniziali();
  }, 10000); 

    });
  }
}
ngOnDestroy() {
  // Fondamentale: pulisci il timer quando chiudi la pagina
  if (this.intervalId) clearInterval(this.intervalId);
}

  // --- AUTENTICAZIONE ---
  login() {
    if (this.passwordInserita === 'admin123') { 
      this.isAdmin = true;
      sessionStorage.setItem('adminAuth', 'true');
      this.caricaDatiIniziali();
    } else {
      alert("Password errata!");
    }
  }

  logout() {
    this.isAdmin = false;
    this.passwordInserita = '';
    sessionStorage.removeItem('adminAuth');
  }

  // --- CARICAMENTO DATI ---
  private caricaDatiIniziali() {
    this.aggiornaIncassi();
    this.caricaStatoSerbatoi();
    this.caricaListaTransazioni();
  }
  
  
  caricaStatoSerbatoi() {
    this.api.getSerbatoi().subscribe({
      next: (data) => {
        this.serbatoi = data;
        data.forEach(s => this.nuoviPrezzi[s.tipoCarburante] = s.prezzoAlLitro);
      },
      error: (err) => console.error("Errore serbatoi:", err)
    });
  }

  eseguiRifornimento(tipo: string) {
    const litri = this.litriRifornimento[tipo];
    if (litri > 0) {
      this.api.rifornimento(tipo, litri).subscribe({
        next: () => {
          alert("Rifornimento completato!");
          this.litriRifornimento[tipo] = 0;
          this.caricaStatoSerbatoi();
          this.caricaDatiIniziali();
        },
        error: (err) => alert("Errore rifornimento")
      });
    }
  }
  caricaListaTransazioni() {
  this.api.getReport().subscribe({
    next: (data: any[]) => {
      // Fidati del backend! Se il backend manda i dati, visualizzali tutti.
      console.log("Transazioni ricevute dal server:", data);
      this.transazioni = data; 
    },
    error: (err) => console.error("Errore report:", err)
  });
}

aggiornaIncassi() {
  this.api.getIncassoStats().subscribe({
    next: (data: IncassoStats) => {
      this.incassoTotale = data.totale;
      this.incassoOggi = data.odierno;
      this.caricaDatiIniziali();
    
    },
    error: (err) => console.error("Errore statistiche:", err)
  });
}
  // --- AZIONI ---
  cambiaPrezzo(s: any) {
    const id = s.id || s.idSerbatoio;
    const prezzo = this.nuoviPrezzi[s.tipoCarburante];

    if (id && prezzo) {
      this.api.aggiornaPrezzo(id, this.nuoviPrezzi[s.tipo]).subscribe(() => {
          this.caricaDatiIniziali();
        
      });
    
  }
  }

  stampaReport() {
    window.print();
  }
}
