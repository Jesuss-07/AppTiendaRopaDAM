DROP DATABASE IF EXISTS tiendaropa;
CREATE DATABASE tiendaropa CHARACTER SET utf8mb4;
USE tiendaropa;

CREATE TABLE USUARIO (
    id_usuario     BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(30) NOT NULL,
    apellido       VARCHAR(30) NOT NULL,
    email          VARCHAR(255) NOT NULL UNIQUE,
    contrasena     VARCHAR(255) NOT NULL,
    telefono       VARCHAR(15),
    estado_usuario BOOLEAN      NOT NULL DEFAULT TRUE, 
--  Esta en true por defecto solo se vera en false si es que comete mas de 
--  tres errores en el inicio de sesion el usuario 
    rol            TINYINT      NOT NULL
);

CREATE TABLE EMPRESA (
    id_empresa         BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_empresa     VARCHAR(100) NOT NULL,
    cif                VARCHAR(20) NOT NULL UNIQUE,
    email_contacto     VARCHAR(255) NOT NULL,
    telefono_contacto  VARCHAR(15),
    direccion_sede     VARCHAR(255) NOT NULL,
    logo_empresa       VARCHAR(255)
);

CREATE TABLE PRODUCTO (
    id_producto       BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_empresa        BIGINT NOT NULL,
    nombre_producto   VARCHAR(150) NOT NULL,
    descripcion       TEXT,
    precio            DECIMAL(10,2) NOT NULL,
    stock             INT NOT NULL,
    talla             VARCHAR(10),
    color             VARCHAR(30),
    categoria         VARCHAR(50),
    imagen_producto   VARCHAR(255),

    CONSTRAINT fk_producto_empresa
        FOREIGN KEY (id_empresa) REFERENCES EMPRESA(id_empresa)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE PEDIDO (
    id_pedido        BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario       BIGINT NOT NULL,
    fecha_pedido     DATETIME NOT NULL,
    estado_pedido    VARCHAR(20) NOT NULL,
    total            DECIMAL(10,2) NOT NULL,

    direccion_cliente  VARCHAR(255) NOT NULL,
    cp_cliente         VARCHAR(10) NOT NULL,
    ciudad_cliente     VARCHAR(50) NOT NULL,
    provincia_cliente  VARCHAR(50) NOT NULL,
    pais_cliente       VARCHAR(50) NOT NULL,

    CONSTRAINT fk_pedido_usuario
        FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE DETALLE_PEDIDO (
    id_detalle       BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_pedido        BIGINT NOT NULL,
    id_producto      BIGINT NOT NULL,
    cantidad         INT NOT NULL,
    precio_unidad  DECIMAL(10,2) NOT NULL,
    subtotal         DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_detalle_pedido
        FOREIGN KEY (id_pedido) REFERENCES PEDIDO(id_pedido)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_detalle_producto
        FOREIGN KEY (id_producto) REFERENCES PRODUCTO(id_producto)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE CLIENTE (
    id_usuario        BIGINT PRIMARY KEY,
    
    direccion1         VARCHAR(255) NOT NULL,
    direccion2        VARCHAR(255),
    cp                VARCHAR(10) NOT NULL,
    pais              VARCHAR(50) NOT NULL,
    ciudad            VARCHAR(50) NOT NULL,
    provincia         VARCHAR(50) NOT NULL,
    puntos            INT DEFAULT 0,
    monedero          DECIMAL(10,2) DEFAULT 0.00,
    -- Datos bancarios POR AHORA NO SE UTILIZARAN ESTOS DATOS PARA NADA DENTRO DEL PROYECTO ES SIMPLEMENTE SI PARA EL DIA DE MAÑANA VEO FACTIBLE LA SIMULACION DE PAGO
    -- token_tarjeta     VARCHAR(255),
    -- ultimos4          VARCHAR(4),
    -- tipo_tarjeta      VARCHAR(20),
    -- tarjeta_simulada  VARCHAR(34),

    CONSTRAINT fk_cliente_usuario
        FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE ADMINISTRADOR (
    id_usuario BIGINT PRIMARY KEY,
    CONSTRAINT fk_admin_usuario
        FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
);

CREATE TABLE VENDEDOR (
    id_usuario       BIGINT PRIMARY KEY,
    id_empresa       BIGINT NOT NULL,
    numero_empleado  VARCHAR(20) NOT NULL,

    CONSTRAINT fk_vendedor_usuario
        FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_vendedor_empresa
        FOREIGN KEY (id_empresa) REFERENCES EMPRESA(id_empresa)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);


