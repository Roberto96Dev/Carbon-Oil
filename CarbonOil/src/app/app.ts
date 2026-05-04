import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
// 1. IMPORTA I COMPONENTI QUI
import { AdminDashboardComponent } from './components/admin/dashboard/admin-dashboard.component';
import { SelfServiceComponent } from './components/user/rifornimento/self-service.component';

@Component({
  selector: 'app-root',
  standalone: true,
  // 2. AGGIUNGILI ALL'ARRAY IMPORTS
  imports: [
    CommonModule, 
    AdminDashboardComponent, 
    SelfServiceComponent
  ],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class AppComponent {
  title = 'CarbonOil';
}