export interface EditarClienteDTO {
    nombre: string;
    apellido: string;
    email: string;
    password?: string;
    telefono: string;
    direccion1: string;
    direccion2: string;
    cp: string;
    pais: string;
    ciudad: string;
    provincia: string;
}