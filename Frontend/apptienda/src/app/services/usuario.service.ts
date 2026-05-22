import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../core/config/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = environment.userUrl;

  constructor(private http: HttpClient) {}

  bloquearUsuario(email: string) {
    return this.http.put(`${this.apiUrl}/bloquear`, null , { params: { email } });
  }

}