set names utf8mb4;
set charset utf8mb4;
drop database if exists startrek;
create database startrek charset utf8mb4;
use startrek;

create table werknemers (
  id int unsigned not null auto_increment primary key,
  voornaam varchar(50) not null,
  familienaam varchar(50) not null,
  budget decimal(10,2) not null default 1000
);

insert into werknemers(voornaam, familienaam) values
('James', 'Kirk'),
('Nyota', 'Uhura'),
('Montgomery', 'Scott'),
('Pavel', 'Chekov'),
('Hikaru', 'Sulu'),
('Janice', 'Rand'),
('Christine', 'Chapel'),
('Tonia', 'Barrows');
 
 create table bestellingen (
	id int unsigned not null auto_increment primary key,
    werknemerId int unsigned not null,
    omschrijving varchar(255) not null,
    bedrag decimal(10,2) not null,
    constraint bestellingenWerknemers foreign key (werknemerId) references werknemers(id)
 );
 
 insert into bestellingen (werknemerId, omschrijving, bedrag) values
 (7, 'schietstoel', 999.99),
 (7, 'ruimtepak', 500),
 (7, 'tube astronautenvoedsel', 17.5);
 

create user if not exists cursist identified by 'cursist';
grant select,insert,update on werknemers to cursist;
grant select,insert,update on bestellingen to cursist;