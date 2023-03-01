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

INSERT INTO film(title,description,duration,start_shooting,nbr_team,visuel) values 
('Etudiant de ITU','La vie quotidien des etudiant de ITU','02:00','2023-03-02',2,'');

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


--Scene1 avec dialogue
insert into scene(title,global_action,time_start,time_end,estimated_time,filmset_id,film_id) values
('seul dans le parc','Sungjae assis sur un banc repensant a son ex, et ensuite discute avec une inconnue','00:10:00','00:10:05','02:00:00',1,1);

insert into dialogue(scene_id,character_id,texte,action) values
(1,1,'Les papillons sont vraiment magnifiques aujourd''hui. Regardez-les virevolter autour de moi','soupirant'),
(1,6,'Oui, ils sont vraiment jolis. Vous avez l''air d''etre dans vos pensees','s''approchant'),
(1,1,'Oui, je me souviens d''une relation qui etait aussi belle et fragile que ces papillons. Mais maintenant, elle est terminee.','en souriant tristement'),
(1,6,'Je suis desole d''entendre ça. Comment allez-vous maintenant ?',''),
(1,1,'Je suis determine retrouver l''amour, quoi qu''il en coute. Je sais que cela ne sera pas facile, mais je suis prêt à faire tout ce qui est en mon pouvoir pour trouver la bonne personne.','determine'),
(1,6,'C''est une belle attitude. Je suis sur que vous trouverez quelqu''un qui vous convient parfaitement. Parfois, il faut juste etre patient et continuer a chercher.','en souriant'),
(1,1,'Merci pour vos mots encourageants','en souriant'),
(1,6,'Bonne chance dans votre recherche, et n''oubliez pas de profiter de la beaute qui vous entoure, meme lorsque les choses sont difficiles','en souriant'),
(1,1,'Merci. Je vais essayer de garder cela a l''esprit. Bonne journee a vous','en se levant'),
(1,6,'Bonne journee a vous aussi','en souriant');


--Scene2 avec dialogue
insert into scene(title,global_action,time_start,time_end,estimated_time,filmset_id,film_id) values
('appel du frere','Son frere appelle Sungjae pour sortir','00:10:07','00:10:11','01:30:00',2,1);

insert into dialogue(scene_id,character_id,texte,action) values
(2,1,'Ces photos me rappellent tellement de souvenirs avec elle','soupirant'),
(2,1,'C''est mon ami qui m''appelle.','regardant son telephone'),
(2,5,'Viens on sors ce soir,cela te fera du bien de sortir un peu','en voix off'),
(2,1,'Je ne sais pas si je devrais y aller. Je ne suis pas sur d''etre pret a voir d''autres couples heureux ensemble','hesitant'),
(2,5,'Tu ne pas rester enferme chez toi eternellement, Sungjae. Il est temps de te changer les idees et de voir de nouveaux visages.','en voix off'),
(2,1,'D''accord, j''accepte l''invitation. Mais je ne promets rien','en soupirant');


--Scene 3 avec Dialogue
insert into scene(title,global_action,time_start,time_end,estimated_time,filmset_id,film_id) values
('durant la soiree','Enviant le bohneur des autres','00:10:12','00:10:13','03:00:00',2,1);

insert into dialogue(scene_id,character_id,texte,action) values
(3,1,'Regardez-les tous, s''amuser, rire, danser ensemble. Ça me rappelle tellement de souvenirs, des moments ou j''etais moi aussi heureux comme eux, en train de danser avec elle.','regardant la foule avec tristesse'),
(3,1,'Regardez-les tous, s''amuser, rire, danser ensemble. Ça me rappelle tellement de souvenirs, des moments ou j''etais moi aussi heureux comme eux, en train de danser avec elle','soupirant');

--scene 4 avec Dialogue
insert into scene(title,global_action,time_start,time_end,estimated_time,filmset_id,film_id) values
('Moment coince en classe','Ils sont coincés dans une salle de classe et ils discutent','00:45:00','00:52:00','02:00:00',6,1);
insert into dialogue(scene_id,character_id,texte,action) values
(4,1,'','Sunjae essaie d''ouvrir la porte'),
(4,2,'Tu vois bien que c''est inutile, nous sommes coincés','soupirant, avec un air blase'),
(4,1, 'c''est toujours mieux que de ne rien faire', 'continue de donner des coups à la porte'),
(4,2,'Laisse moi faire alors','se deplace viens Sunjae et essaie d''ouvrir la porte grace a sa broche, puis la porte fini par s''ouvrir  '),
(4,1,'impressionnant!, je te laisse, je suis un peu presse','Sunjae sort avec elle et il lui fait un signe de tête');

--scene 5 avec Dialogue
insert into scene(title,global_action,time_start,time_end,estimated_time,filmset_id,film_id) values
('Rendez-surprise','Sunjae a ete par son frere de bien s''habille et son frere le traine en ville, son frere a organise un rendez vous avec Joy et ca devient genant',
'01:05:00','01:12:00','01:00:00',1,1);
insert into dialogue(scene_id,character_id,texte,action) values
(5,1,'Poruquoi tu m''as force a sortir et a m''habiller comme ca','se fait trainer par son frere, avec un air ennuye'),
(5,5,'Parce que j''ai une surprise pour toi je t''ai dit','tire Sunjae'),
(5,1, 'Ca fait des heures qu''on marche', 'rale de plus en plus'),
(5,5,'Arrete de raler, tu vas adorer ma surprise','Continue de tirer Sunjae'),
(5,1,'euh, Joy!','Sunjae est etonne'),
(5,2,'Bonsoir Sunjae','Elle est joliment habille et le sourit'),
(5,5,'Alors je vous laisse', 'Peniel s''en va et Sunjae rougit')
;


--scene 6 avec Dialogue
