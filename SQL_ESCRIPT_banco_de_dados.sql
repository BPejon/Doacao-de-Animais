create database if not exists adocao
	character set utf8
    collate utf8_general_ci;
    
use adocao;

#o nosso banco de dados tem 4 tabelas, de fisicas, de juridicas, de pessoas em geral, e de animais
create table if not exists fisicas(
	id_fisica int auto_increment,
    cpf varchar(14) not null unique,
    
    primary key(id_fisica)
);

create table if not exists juridicas(
	id_juridica int auto_increment,
    cnpj varchar(18) not null unique,
    responsavel varchar(100),
    
    primary key(id_juridica)
);

create table if not exists pessoas(
	id_pessoa int auto_increment,
	
    email varchar(100) not null unique,
    telefone varchar(14) not null,
    nome varchar(100), -- nome da pessoa no caso de pessoa fisica, nome da empresa no caso de juridica
    
    uf varchar(2) not null,
	cidade varchar(50) not null,
    endereco varchar(100) not null,
    
    senha varchar(100),
    
    id_fisica int,
    id_juridica int,
	
    primary key(id_pessoa),
    foreign key(id_fisica) references fisicas(id_fisica),
    foreign key(id_juridica) references juridicas(id_juridica)
    
);

create table if not exists animais(
	
    id_animal int auto_increment,
    
	nome varchar(100) not null,
    especie varchar(50) not null,
    descricao varchar(200) not null,
    sexo varchar(10),
    idade int, 
    raca varchar(50),
    image_name varchar(70),
    id_pessoa int not null,
    condicao varchar(30),
    
    primary key(id_animal),
    foreign key(id_pessoa) references pessoas(id_pessoa)
    
);