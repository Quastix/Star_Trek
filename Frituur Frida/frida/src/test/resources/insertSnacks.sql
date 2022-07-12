    /*
We maken SnackRepositoryTest robuust en zelfstandig. De test voegt zelf een record
toe en test findById met dit record. Gezien de test op het einde zijn transactie rollbackt,
is dit record na de test verdwenen in de database.
*/
insert into snacks (naam, prijs)
values ('test', 10);