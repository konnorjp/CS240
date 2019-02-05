create table users
(
	username varchar(255) not null,
  password varchar(255) not null,
	email varchar(255) not null,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  gender varchar(1) not null,
  person_id int not null,
  constraint ck_gender check (gender in ('f','m'))
);


create table persons
(
	id integer not null primary key autoincrement,
  descendant varchar(255) not null,
  first_name varchar(255) not null,
  last_name varchar(255) not null,
  gender varchar(1) not null,
  father_id int,
  mother_id int,
  spouse_id int
);


create table events
(
  id integer not null primary key autoincrement,
  descendant varchar(255) not null,
  person_id int not null,
  latitude float not null,
  longitude float not null,
  country varchar(255) not null,
  city varchar(255) not null,
  event_type varchar(255) not null,
  year int not null,
  constraint ck_event check (event_type in ('birth', 'baptism', 'christening', 'marriage', 'death'))
);
