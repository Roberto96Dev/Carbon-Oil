import { Routes } from '@angular/router';
import { SelfServiceComponent } from './components/user/rifornimento/self-service.component'; 
import { AdminDashboardComponent } from './components/admin/dashboard/admin-dashboard.component';

export const routes: Routes = [
  { 
    path: 'self-service', 
    component: SelfServiceComponent 
  },
  { 
    path: 'admin', 
    component: AdminDashboardComponent 
  },
  { 
    path: '', 
    redirectTo: 'self-service', 
    pathMatch: 'full' 
  }, // Se l'URL è vuoto, va al self-service
  { 
    path: '**', 
    redirectTo: 'self-service' 
  } // Se scrivi una roba inesistente, torna al self-service
];