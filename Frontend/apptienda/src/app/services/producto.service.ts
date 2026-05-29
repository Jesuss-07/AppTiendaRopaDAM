import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../core/config/environment';
import { Observable } from 'rxjs';
import { CrearProductoDTO } from '../model/crear-producto.dto';
import { CrearProductoAdminDTO } from '../model/crear-producto-admin.dto';
import { ListarProductosVendedorDTO } from '../model/Listar-productos-vendedor.dto';
import { EditarProductoDTO } from '../model/editar-producto.dto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = environment.productUrl;
  constructor(private http: HttpClient) {}

  anadirProductoVendedor(producto: CrearProductoDTO): Observable<CrearProductoDTO> {
    return this.http.post<CrearProductoDTO>(`${this.apiUrl}/crear`, producto);
  }

  anadirProductoAdmin(producto: CrearProductoAdminDTO): Observable<CrearProductoAdminDTO> {
    return this.http.post<CrearProductoAdminDTO>(`${this.apiUrl}/crear/admin`, producto);
  }

  editarProducto(id: number, productoDTO: EditarProductoDTO): Observable<EditarProductoDTO> {
    return this.http.put<EditarProductoDTO>(`${this.apiUrl}/editar/${id}`, productoDTO);
  }

  obtenerProducto(id: number): Observable<EditarProductoDTO> {
    return this.http.get<EditarProductoDTO>(`${this.apiUrl}/obtener/${id}`);
  }

  listarTodosLosProductos(): Observable<ListarProductosVendedorDTO[]> {
    return this.http.get<ListarProductosVendedorDTO[]>(`${this.apiUrl}/listar/productos`);
  }

  eliminarProducto(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${id}`);
  }

  listarProductos(): Observable<ListarProductosVendedorDTO[]> {
    return this.http.get<ListarProductosVendedorDTO[]>(`${this.apiUrl}/listar`);
  }

}