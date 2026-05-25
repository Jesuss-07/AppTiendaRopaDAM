import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "../core/config/environment";
import { EmpresaDTO } from "../model/empresa.dto";
import { Observable } from "rxjs";
@Injectable({
    providedIn: 'root'
})

export class EmpresaService {
    private apiUrl = environment.empresaUrl;

    constructor(private http: HttpClient) {}

    registroEmpresa(empresa: any) {
        return this.http.post(`${this.apiUrl}/registro`, empresa);
    }

    obtenerEmpresas(): Observable<EmpresaDTO[]> {
        return this.http.get<EmpresaDTO[]>(`${this.apiUrl}/lista`);
    }

}