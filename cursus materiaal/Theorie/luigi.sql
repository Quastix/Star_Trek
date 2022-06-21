set names utf8mb4;
set charset utf8mb4;

drop database if exists luigi;
create database luigi charset utf8mb4;
use luigi;


create table pizzas (
  id int not null auto_increment primary key,
  naam varchar(45) not null,
  prijs decimal(10,2) not null,
  pikant bit not null
);

insert into pizzas(naam,prijs,pikant) values 
('Prosciutto', 4, true),
('Margheritta', 5, false),
('Calzone', 4, false),
('Fungi & Olive', 5, false);

create user if not exists cursist identified by 'cursist';
grant select,insert,update,delete on pizzas to cursist;