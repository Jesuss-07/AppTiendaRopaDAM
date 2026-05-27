import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { environment } from "../core/config/environment";
import { EmpresaDTO } from "../model/empresa.dto";
import { Observable } from "rxjs";
import { ActualizarEmpresaDTO } from "../model/actualizar-empresa.dto";
import { VistaEmpresaDTO } from "../model/vista-empresa.dto";
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

    obtenerEmpresaPorId(id: number): Observable<ActualizarEmpresaDTO> {
        return this.http.get<ActualizarEmpresaDTO>(`${this.apiUrl}/${id}`);
    }

    obtenerVistaEmpresa(id: number): Observable<VistaEmpresaDTO> {
        return this.http.get<VistaEmpresaDTO>(`${this.apiUrl}/vista/${id}`);
    }

    actualizarEmpresa(id: number, empresa: any) {
        return this.http.put(`${this.apiUrl}/${id}`, empresa);
    }

    borrarEmpresa(id: number) {
        return this.http.delete(`${this.apiUrl}/${id}`);
    }
    
}