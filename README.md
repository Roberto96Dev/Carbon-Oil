

⛽ CarbonOil - Fuel Management System
CarbonOil è una piattaforma gestionale full-stack progettata per l'automazione e la supervisione di un distributore di carburante moderno. Il sistema adotta un'architettura disaccoppiata con un backend robusto in Spring Boot e una dashboard reattiva sviluppata in Angular, simulando l'intero ciclo di vita del rifornimento, dal totem self-service alla gestione amministrativa.


🚀 Funzionalità Principali👤 Customer Interface (Angular SPA)L'interfaccia utente simula un totem digitale intuitivo:Selezione Colonnina:

 Visualizzazione dinamica delle postazioni disponibili/occupate.User Flow Guidato: Selezione del carburante (Benzina, Diesel, GPL, 
 
 Metano) con prezzi aggiornati in tempo reale tramite API.Simulazione Erogazione: Calcolo automatico del volume in base al credito 
 
 inserito:$$Litri = \frac{Importo (€)}{Prezzo al Litro (€/L)}$$Ricevuta Digitale: Generazione del riepilogo transazione post-erogazione.



🔐 Admin Dashboard (Restricted Area)

Pannello di controllo per la gestione operativa e finanziaria:

Pricing Engine: Modifica dinamica dei listini prezzi con aggiornamento immediato sul frontend.

Inventory Management: Monitoraggio dei livelli delle cisterne con alert visivi per scorte in esaurimento e funzione di Refill.

Business Intelligence: Storico transazioni filtrabile e reportistica dei ricavi totali.

Log di Sistema: Monitoraggio dello stato di salute delle colonnine.



🛠️ Stack Tecnologico

Backend


Java 17+

Spring Boot 3.x

Spring Data JPA: Per la persistenza dei dati.

H2 Database / PostgreSQL: Gestione dei serbatoi, transazioni e prezzi.

Spring Security: Protezione delle API amministrative.

Frontend


Angular 16+

TypeScript

RxJS: Gestione degli stream di dati e polling per lo stato dei serbatoi.

Tailwind CSS / Angular Material: Per un'interfaccia moderna e responsive.



🏗️ Architettura del Sistema


Il progetto segue un pattern Client-Server:

REST API: Il backend espone endpoint per la gestione del catalogo carburanti e il processing delle transazioni.

State Management: Il sistema garantisce l'atomicità delle operazioni (es. non è possibile erogare più carburante di quello presente nel serbatoio).



📖 Scenario di Utilizzo (Esempio)


Admin: Accede al pannello e imposta il Diesel a 1.75€/L.

Utente: Seleziona la "Colonna 1" e sceglie "Diesel".

Pagamento: L'utente inserisce 20€.

Sistema:

Autorizza l'erogazione di 11.42 Litri.

Sottrae il volume dalla cisterna DIESEL_TANK.

Registra la transazione nel database.

Admin: Visualizza l'incremento del fatturato nel grafico dei ricavi.

🤝 Contributi
Le pull request sono benvenute. Per modifiche importanti, ti preghiamo di aprire prima un ticket per discutere ciò che vorresti cambiare.

📄 Licenza
Questo progetto è distribuito sotto licenza MIT. Vedi il file LICENSE per ulteriori dettagli.

Progetto sviluppato da [Roberto Virnuccio].