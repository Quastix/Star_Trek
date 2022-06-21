insert into dagverkopen(snackid, datum, aantal)
values ((select id from snacks where naam='test'), curdate(), 10);