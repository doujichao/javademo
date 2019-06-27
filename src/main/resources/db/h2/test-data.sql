insert  into `singer`(`id`,`first_name`,`last_name`,`birth_date`) values (1,'John','Mayer','1977-10-16'),(2,'Eric','Clapton','1945-03-30'),(3,'John','Bitler','1975-04-01');
insert  into `album`(`id`,`singer_id`,`title`,`release_date`) values (1,1,'The Search For Everything','2017-01-20'),(2,1,'Battle Studies','2009-11-17'),(3,2,'From The Cradle','1994-09-13');
insert  into `instrument`(`instrument_id`) values ('Drums'),('Guitar'),('Piano'),('Synthesiner'),('Voice');
insert  into `singer_instrument`(`singer_id`,`instrument_id`) values (1,'Piano'),(2,'Guitar'),(1,'Guitar');
