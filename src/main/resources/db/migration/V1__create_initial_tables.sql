create table usuarios(
    id bigint not null auto_increment,
    nome varchar(255) not null,
    email varchar(255) not null unique,
    senha varchar(255) not null,
    perfil enum('ADMIN', 'USER') not null,
    primary key(id)
);

create table cursos(
    id bigint not null auto_increment,
    curso varchar(255) not null unique,
    categoria enum('PROGRAMACAO') not null,
    primary key(id)
);

create table topicos(
    id bigint not null auto_increment,
    titulo varchar(255) not null unique,
    mensagem varchar(512) not null unique,
    data_criacao timestamp not null,
    status enum('ABERTO', 'SOLUCIONADO') not null,
    autor varchar(255) not null,
    curso_id bigint not null,
    primary key(id),
    foreign key(curso_id) references cursos(id)
);

create table respostas(
    id bigint not null auto_increment,
    topico_id bigint not null,
    mensagem varchar(512) not null,
    data_criacao timestamp not null,
    autor varchar(255) not null,
    solucao boolean not null default 0,
    primary key(id),
    foreign key(topico_id) references topicos(id) ON DELETE CASCADE,
    unique (topico_id, mensagem)
);