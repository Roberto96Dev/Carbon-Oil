import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app';
import { config } from './app/app.config.server';

// Non chiamare bootstrapApplication() qui direttamente!
// Esporta la funzione in modo che il motore SSR possa gestirla correttamente.
const bootstrap = () => bootstrapApplication(AppComponent, config);

export default bootstrap;