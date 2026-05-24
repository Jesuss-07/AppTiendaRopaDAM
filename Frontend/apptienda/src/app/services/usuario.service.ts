import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../core/config/environment';
import { Observable } from 'rxjs';
import { EditarClienteDTO } from '../model/editar-cliente.dto';

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


}