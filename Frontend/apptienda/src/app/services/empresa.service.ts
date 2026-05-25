import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { reportUnhandledError } from "rxjs/internal/util/reportUnhandledError";
import { jwtDecode } from "jwt-decode";
import { environment } from "../core/config/environment";

@Injectable({
    providedIn: 'root'
})

export class EmpresaService {
    private apiUrl = environment.empresaUrl;

    constructor(private http: HttpClient) {}

    registroEmpresa(empresa: any) {
        return this.http.post(`${this.apiUrl}/registro`, empresa);
    }

    getEmpresas() {
        return this.http.get(`${this.apiUrl}/lista`);
    }

}