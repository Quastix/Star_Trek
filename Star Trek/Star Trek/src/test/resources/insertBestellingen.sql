/*
We maken BestellingRepositoryTest robuust en zelfstandig. De test voegt zelf een record
toe en test findBestellingByWerknemerId met dit record.
Gezien de test op het einde zijn transactie rollbackt,
is dit record na de test verdwenen in de database.
*/
insert into bestellingen (werknemerId, omschrijving, bedrag)
values (1, 'test1', 50), (2, 'test2', 20), (1, 'test3', 30);