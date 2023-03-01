--acteur
INSERT INTO actor(name,birthdate,contact,gender) values ('Shin won ho','1991-10-23','+26208102030',1);
INSERT INTO actor(name,birthdate,contact,gender) values ('Lee jong suk','1989-09-14','+26233102030',1);
INSERT INTO actor(name,birthdate,contact,gender) values ('Nam joo hyuk','1995-02-02','+26208202030',1);
INSERT INTO actor(name,birthdate,contact,gender) values ('Moon ga young','1999-01-10','+26203102030',2);
INSERT INTO actor(name,birthdate,contact,gender) values ('Park shin hye','1990-05-06','+26256102030',2);
INSERT INTO actor(name,birthdate,contact,gender) values ('Park min young','1980-06-03','+26208202222',2);

--film
INSERT INTO film(title,description,duration,start_shooting,nbr_team,visuel) values 
('Butterfly graves','Comme de la tombe de papillon la relation de sungjae qui était beaux et fragile a pris fin.Mais il est determiner a retrouver l''amour','01:30','2023-03-01',2,'');

--Caractere
INSERT INTO character(name,description,gender,film_id,actor_id) values
('Sunjae','Role principal masculin,celui qui a perdu l''amour',1,1,1),
('Joy','Role principal feminin,celle que sungjae va rencontrer apres esperance',2,1,4),
('Dia','celle que a quitte  sungjae ',2,1,5),
('Crush','Meilleur ami de Joy',1,1,3),
('Peniel','Grand frere de sungjae',1,1,2),
('Inconnu','une personne balladant dans le parc',1,1,6);

--Plateau
INSERT INTO filmset(name,type_id) values
('parc',1),
('chambre de sungjae',2),
('cafe',1),
('piste de danse',1),
('concert',1),
('lieu travail',2);


--Scene avec dialogue



