CREATE DATABASE film;

CREATE SEQUENCE film_id_seq START WITH 1;

CREATE SEQUENCE actor_id_seq START WITH 1;

CREATE SEQUENCE character_id_seq START WITH 1;

CREATE SEQUENCE filmset_id_seq START WITH 1;

CREATE SEQUENCE scene_id_seq START WITH 1;

CREATE SEQUENCE dialogue_id_seq START WITH 1;

CREATE SEQUENCE planning_id_seq START WITH 1;

CREATE  TABLE film ( 
	id                   integer DEFAULT nextval('film_id_seq'::regclass) NOT NULL  ,
	title                varchar(60)  NOT NULL ,
	description          text  NOT NULL ,
	duration             time DEFAULT '01:00' NOT NULL ,
	start_shooting       date  NOT NULL ,
	nbr_team             integer DEFAULT 1 NOT NULL ,
	visuel               text  ,
	CONSTRAINT pk_film_id PRIMARY KEY ( id )
 );

CREATE  TABLE gender ( 
	id                   integer  NOT NULL ,
	name                 varchar(100)  NOT NULL ,
	CONSTRAINT pk_gender_id PRIMARY KEY ( id )
 );

CREATE  TABLE settings ( 
	id                   integer  NOT NULL ,
	name                 varchar(100)  NOT NULL ,
	"value"              double precision  NOT NULL ,
	CONSTRAINT pk_settings_id PRIMARY KEY ( id )
 );

CREATE  TABLE status_planning ( 
	id                   integer  NOT NULL ,
	name                 varchar(50)  NOT NULL ,
	CONSTRAINT pk_status_planning_id PRIMARY KEY ( id )
 );

CREATE  TABLE type_filmset ( 
	id                   integer  NOT NULL ,
	name                 varchar(50)  NOT NULL ,
	CONSTRAINT pk_type_filmset_id PRIMARY KEY ( id )
 );

CREATE  TABLE actor ( 
	id                   serial  NOT NULL ,
	name                 varchar(100)  NOT NULL ,
	birthdate            date  NOT NULL ,
	contact              varchar(100)  NOT NULL ,
	gender               integer  NOT NULL ,
	CONSTRAINT pk_personnage_id PRIMARY KEY ( id )
 );

CREATE  TABLE "character" ( 
	id                   integer DEFAULT nextval('character_id_seq'::regclass) NOT NULL  ,
	name                 varchar(100)  NOT NULL ,
	description          text  NOT NULL ,
	gender               integer  NOT NULL ,
	film_id              integer  NOT NULL ,
	actor_id             integer  NOT NULL ,
	CONSTRAINT pk_p_id PRIMARY KEY ( id ),
	CONSTRAINT idx_p UNIQUE ( name ) 
 );

CREATE  TABLE filmset ( 
	id                   integer DEFAULT nextval('filmset_id_seq'::regclass) NOT NULL  ,
	name                 varchar(100)  NOT NULL ,
	type_id              integer  NOT NULL ,
	CONSTRAINT pk_filmset_id PRIMARY KEY ( id )
 );

CREATE  TABLE scene ( 
	id                   integer DEFAULT nextval('scene_id_seq'::regclass) NOT NULL  ,
	title                varchar(100)  NOT NULL ,
	global_action        text  NOT NULL ,
	time_start           time  NOT NULL ,
	time_end             time  NOT NULL ,
	estimated_time       time  NOT NULL ,
	filmset_id           integer  NOT NULL ,
	film_id              integer  NOT NULL ,
	CONSTRAINT pk_scenery_id PRIMARY KEY ( id )
 );

CREATE  TABLE dialogue ( 
	id                   integer DEFAULT nextval('dialogue_id_seq'::regclass) NOT NULL  ,
	scene_id             integer  NOT NULL ,
	character_id         integer  NOT NULL ,
	texte                text  NOT NULL ,
	"action"             text   ,
	CONSTRAINT pk_dialogue_id PRIMARY KEY ( id )
 );

CREATE  TABLE planning ( 
	id                   integer DEFAULT nextval('planning_id_seq'::regclass) NOT NULL  ,
	scene_id             integer  NOT NULL ,
	status               integer DEFAULT 1 NOT NULL ,
	"date"               timestamp  NOT NULL ,
	CONSTRAINT pk_planning_id PRIMARY KEY ( id )
 );

ALTER TABLE actor ADD CONSTRAINT fk_actor_gender FOREIGN KEY ( gender ) REFERENCES gender( id );

ALTER TABLE "character" ADD CONSTRAINT fk_character_gender FOREIGN KEY ( gender ) REFERENCES gender( id );

ALTER TABLE "character" ADD CONSTRAINT fk_character_actor FOREIGN KEY ( actor_id ) REFERENCES actor( id );

ALTER TABLE "character" ADD CONSTRAINT fk_character_film FOREIGN KEY ( film_id ) REFERENCES film( id );

ALTER TABLE dialogue ADD CONSTRAINT fk_dialogue_scene FOREIGN KEY ( scene_id ) REFERENCES scene( id );

ALTER TABLE dialogue ADD CONSTRAINT fk_dialogue_character FOREIGN KEY ( character_id ) REFERENCES "character"( id );

ALTER TABLE filmset ADD CONSTRAINT fk_filmset_type_filmset FOREIGN KEY ( type_id ) REFERENCES type_filmset( id );

ALTER TABLE planning ADD CONSTRAINT fk_planning_status_planning FOREIGN KEY ( status ) REFERENCES status_planning( id );

ALTER TABLE planning ADD CONSTRAINT fk_planning_scene FOREIGN KEY ( scene_id ) REFERENCES scene( id );

ALTER TABLE scene ADD CONSTRAINT fk_scene_film FOREIGN KEY ( film_id ) REFERENCES film( id );

ALTER TABLE scene ADD CONSTRAINT fk_scene_filmset FOREIGN KEY ( filmset_id ) REFERENCES filmset( id );

--data

INSERT INTO gender VALUES(1, 'male'),(2, 'female');

INSERT INTO type_filmset VALUES(1, 'public'),(2, 'private');

INSERT INTO status_planning VALUES(1, 'planned'),(2, 'in progress'),(3, 'done'), (4, 'canceled');

INSERT INTO settings VALUES(1, 'work hour', 8);