
# create table usuarios(
#     id int auto_increment primary key,
#     username varchar(100) not null unique ,
#     password varchar(200) not null,
#     role enum('ROLE_ADMIN', 'ROLE_CLIENTE'),
#     criado_por varchar(255),
#     data_criacao datetime(6),
#     modificado_por varchar (255),
#     data_modificacao datetime(6)
#
# );

insert into demo_park_test.usuarios( username, password, role)values ( 'ana@email.com', '123456', 'ROLE_ADMIN'  );
insert into demo_park_test.usuarios( username, password, role)values ( 'bia@email.com', '123456', 'ROLE_CLIENTE'  );
insert into demo_park_test.usuarios( username, password, role)values ( 'bob@email.com', '123456', 'ROLE_CLIENTE'  );

select * from usuarios;








