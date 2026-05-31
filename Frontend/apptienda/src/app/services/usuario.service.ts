import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../core/config/environment';
import { Observable } from 'rxjs';
import { EditarClienteDTO } from '../model/editar-cliente.dto';
import { VistaVendedorDTO } from '../model/vista-vendedor.dto';
import { EditarAdministradorDTO } from '../model/editarr-administrador.dto';
import { UsuarioDTO } from '../model/Usuario.dto';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = environment.userUrl;

  constructor(private http: HttpClient) {}

  bloquearUsuario(email: string) {
    return this.http.put(`${this.apiUrl}/bloquear`, null , { params: { email } });
  }

  getCliente(): Observable<any> {
    return this.http.get(`${this.apiUrl}/cliente/me`);
  }

  getVendedor(): Observable<any> {
    return this.http.get(`${this.apiUrl}/vendedor/me`);
  }

  getAdministrador(): Observable<any> {
    return this.http.get(`${this.apiUrl}/administrador/me`);
  }

  updateCliente(clienteDTO: EditarClienteDTO): Observable<any> {
    return this.http.put(
      `${this.apiUrl}/cliente/editar`,
      clienteDTO,
      {
        headers: { 'Content-Type': 'application/json' }
      }
    );
  }

  updateVendedor(vendedorDTO: any): Observable<any> {
    return this.http.put(
      `${this.apiUrl}/vendedor/editar`,
      vendedorDTO,
      {
        headers: { 'Content-Type': 'application/json' }
      }
    );
  }

  updateAdministrador(administradorDTO: EditarAdministradorDTO): Observable<any> {
    return this.http.put(
      `${this.apiUrl}/administrador/editar`,
      administradorDTO,
      {
        headers: { 'Content-Type': 'application/json' }
      }
    );
  }

  obtenerVendedoresPorEmpresa(cif: string): Observable<VistaVendedorDTO[]> {
    return this.http.get<VistaVendedorDTO[]>(
        `${this.apiUrl}/vendedores/empresa/${cif}`
    );
  }

  listarUsuarios(): Observable<UsuarioDTO[]> {
    return this.http.get<UsuarioDTO[]>(`${this.apiUrl}/listar`);
  }
}