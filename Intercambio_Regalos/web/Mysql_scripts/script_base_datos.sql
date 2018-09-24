drop database if exists convivio;
create database convivio;
use convivio;
create table usuario (
    correo varchar(20) primary key not null, 
    nombre varchar(50), 
    alias varchar(10), 
    contrasenia varchar(10)
);

create table amigos (
    correo_usuario varchar(20),
    correo_amigo varchar(20),
    foreign key (correo_usuario) references usuario(correo) on delete cascade,
    foreign key (correo_amigo) references usuario(correo) on delete cascade
);

create table tema(
    id int primary key not null auto_increment,
    nombre_tema varchar(10)
);

create table intercambio (
    id int primary key not null auto_increment,
    nombre varchar(20),
    id_tema int,
    monto_maximo decimal(6,2),
    fecha_limite date,
    fecha_intercambio date,
    comentarios varchar(150),
    foreign key (id_tema) references tema(id)
);

create table usuario_intercambio(
    id_usuario varchar(20),
    id_intercambio int,
    estado_aceptacion boolean,
    id_intercambiar varchar(20),
    creador_intercambio boolean,
    foreign key (id_usuario) references usuario(correo) on delete cascade,
    foreign key (id_intercambio) references intercambio(id) on delete cascade
);

insert into tema (nombre_tema) values ("Libros"),("Chocolates"),("Calcetines"),("Gorros");