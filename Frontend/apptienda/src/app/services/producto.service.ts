import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../core/config/environment';
import { Observable } from 'rxjs';
import { CrearProductoDTO } from '../model/crear-producto.dto';
import { ListarProductosVendedorDTO } from '../model/Listar-productos-vendedor.dto';
import { EditarProductoDTO } from '../model/editar-producto.dto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = environment.productUrl;
  constructor(private http: HttpClient) {}

  anadirProducto(producto: CrearProductoDTO): Observable<CrearProductoDTO> {
    return this.http.post<CrearProductoDTO>(`${this.apiUrl}/crear`, producto);
  }

  editarProducto(id: number, productoDTO: EditarProductoDTO): Observable<EditarProductoDTO> {
    return this.http.put<EditarProductoDTO>(`${this.apiUrl}/editar/${id}`, productoDTO);
  }

  obtenerProducto(id: number): Observable<EditarProductoDTO> {
    return this.http.get<EditarProductoDTO>(`${this.apiUrl}/obtener/${id}`);
  }

  //METODO NO LISTO
  eliminarProducto(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/eliminar/${id}`);
  }

  listarProductos(): Observable<ListarProductosVendedorDTO[]> {
    return this.http.get<ListarProductosVendedorDTO[]>(`${this.apiUrl}/listar`);
  }

}