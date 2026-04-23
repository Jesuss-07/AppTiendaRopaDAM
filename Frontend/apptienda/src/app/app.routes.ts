import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { ClienteComponent } from './pages/registrar/cliente/cliente.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'registro/cliente', component: ClienteComponent },
    { path: '', redirectTo: 'login' , pathMatch: 'full' }
];
