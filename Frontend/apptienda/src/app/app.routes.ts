import { Routes } from '@angular/router';
import { InicioComponent } from './pages/inicio/inicio.component';
import { LoginComponent } from './pages/login/login.component';
import { EleccionComponent } from './pages/registrar/eleccion/eleccion.component';
import { ClienteComponent } from './pages/registrar/cliente/cliente.component';
import { EditorClienteComponent } from './pages/usuario/editor-cliente/editor.component';
import { RegistroEmpresaComponent } from './pages/registrar/empresa/empresa.component';
import { VendedorComponent } from './pages/registrar/vendedor/vendedor.component';
import { EditorVendedorComponent } from './pages/usuario/editor-vendedor/editor.component';
import { EmpresaComponent } from './pages/empresa/listado-empresas/empresa.component';
import { EditarEmpresaComponent } from './pages/empresa/editar-empresa/empresa.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'registro', component: EleccionComponent },
    { path: 'registro/cliente', component: ClienteComponent },
    { path: 'registro/vendedor', component: VendedorComponent },
    { path: 'registro/empresa', component: RegistroEmpresaComponent },
    { path: 'inicio', component: InicioComponent },
    { path: 'editor/cliente', component: EditorClienteComponent },
    { path: 'editor/vendedor', component: EditorVendedorComponent },
    { path: 'empresa', component: EmpresaComponent },
    { path: 'empresa/editar/:id', component: EditarEmpresaComponent },
    { path: '', redirectTo: 'login' , pathMatch: 'full' }
];
