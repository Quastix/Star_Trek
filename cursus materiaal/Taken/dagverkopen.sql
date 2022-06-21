use frida;
drop table if exists dagVerkopen;
create table dagVerkopen (
  snackId int unsigned not null,
  datum date not null,
  aantal int unsigned not null,
  primary key (snackId, datum),
  constraint fk_dagVerkopen_Snacks foreign key (snackId) references snacks(id)
);
  
insert into dagVerkopen(snackId, datum, aantal)
   select id, date_add(curdate(), interval -length(naam) day), length(naam)*2 from snacks where naam not like 'B%';
insert into dagVerkopen(snackId, datum, aantal)
  select id, date_add(curdate(), interval -length(naam)*2 day), length(naam) from snacks where naam not like 'B%';
insert into dagVerkopen(snackId, datum, aantal)
  select id, date_add(curdate(), interval -length(naam)*3 day), length(naam)*5 from snacks where naam not like 'B%';

grant select, insert on dagVerkopen to cursist;