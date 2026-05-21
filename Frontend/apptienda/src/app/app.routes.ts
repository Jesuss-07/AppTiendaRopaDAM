import { Routes } from '@angular/router';
import { InicioComponent } from './pages/inicio/inicio.component';
import { LoginComponent } from './pages/login/login.component';
import { EleccionComponent } from './pages/registrar/eleccion/eleccion.component';
import { ClienteComponent } from './pages/registrar/cliente/cliente.component';
import { EmpresaComponent } from './pages/registrar/empresa/empresa.component';
import { VendedorComponent } from './pages/registrar/vendedor/vendedor.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'registro', component: EleccionComponent },
    { path: 'registro/cliente', component: ClienteComponent },
    { path: 'registro/vendedor', component: VendedorComponent },
    { path: 'registro/empresa', component: EmpresaComponent },
    { path: 'inicio', component: InicioComponent },
    { path: '', redirectTo: 'login' , pathMatch: 'full' }
];
