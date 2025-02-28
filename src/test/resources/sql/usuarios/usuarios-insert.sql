
create table usuario(
    id int auto_increment primary key,
    username varchar(100),
    password varchar(200),
    role enum('ROLE_ADMIN', 'ROLE_CLIENTE'),
    criado_por varchar(255),
    data_criacao datetime(6),
    modificado_por varchar (255),
    data_modificacao datetime(6)

);

insert into demo_park_test.usuarios(id, username, password, role)values (100, 'ana@email.com', '123456', 'ROLE_ADMIN'  );
insert into demo_park_test.usuarios(id, username, password, role)values (101, 'bia@email.com', '123456', 'ROLE_CLIENTE'  );
insert into demo_park_test.usuarios(id, username, password, role)values (102, 'bob@email.com', '123456', 'ROLE_CLIENTE'  );









