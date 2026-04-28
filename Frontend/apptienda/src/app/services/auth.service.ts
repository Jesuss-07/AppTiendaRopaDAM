import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { reportUnhandledError } from "rxjs/internal/util/reportUnhandledError";
import { jwtDecode } from "jwt-decode";
import { environment } from "../core/config/environment";

@Injectable({
    providedIn: 'root'
})

export class AuthService {
    private apiUrl = environment.authUrl;

    constructor(private http: HttpClient) {}

    login(usuario: any) {
        return this.http.post(`${this.apiUrl}/login`, usuario);
    }

    registroCliente(cliente: any) {
        return this.http.post(`${this.apiUrl}/registro/cliente`, cliente);
    }

    registroVendedor(vendedor: any) {
        return this.http.post(`${this.apiUrl}/registro/vendedor`, vendedor);
    }

    guardarToken(token: string) {
        localStorage.setItem('token', token);
    }

    obtenerToken() {
        return localStorage.getItem('token');
    }

    logout() {
        localStorage.removeItem('token');
    }

    estadoAunteticado() {
        return this.obtenerToken() != null;
    }

    getRol(): string | null {
        const token = this.obtenerToken();
        if (!token) return null;

        const datos: any = jwtDecode(token);
        return datos.rol;
    }
}