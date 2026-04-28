import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { ClienteComponent } from './pages/registrar/cliente/cliente.component';
import { EmpresaComponent } from './pages/registrar/empresa/empresa.component';
import { VendedorComponent } from './pages/registrar/vendedor/vendedor.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'registro/cliente', component: ClienteComponent },
    { path: 'registro/vendedor', component: VendedorComponent },
    { path: 'registro/empresa', component: EmpresaComponent },
    { path: '', redirectTo: 'login' , pathMatch: 'full' }
];
