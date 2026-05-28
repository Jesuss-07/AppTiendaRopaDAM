import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../core/config/environment';
import { Observable } from 'rxjs';
import { ListarProductosVendedorDTO } from '../model/Listar-productos-vendedor.dto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = environment.productUrl;
  constructor(private http: HttpClient) {}

  anadirProducto(producto: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, producto);
  }

  //METODO NO LISTO
  editarProducto(id: number, productoDTO: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/editar/${id}`, productoDTO);
  }

  //METODO NO LISTO
  eliminarProducto(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${id}`);
  }

  listarProductos(): Observable<ListarProductosVendedorDTO[]> {
    return this.http.get<ListarProductosVendedorDTO[]>(`${this.apiUrl}/listar`);
  }

}