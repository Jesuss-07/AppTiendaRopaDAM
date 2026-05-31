import { Rol } from "./rol.enum";

export interface UsuarioDTO {
    id: number;
    nombre: string;
    apellido: string;
    email: string;
    rol: Rol;
}