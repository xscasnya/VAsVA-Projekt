CREATE TABLE users (
  id SERIAL PRIMARY KEY,  
  email varchar(20) NOT NULL,
  nickname varchar(10) NOT NULL,
  password varchar(20) NOT NULL,
  registered_at timestamp NOT NULL,
);

CREATE TABLE room_type(
	id SERIAL PRIMARY KEY,
	type varchar(10)	
);

CREATE TABLE room (
  id SERIAL PRIMARY KEY,
  name varchar(30) NOT NULL,
  password varchar(20) NOT NULL,
  type_id int references room_type(id),
  created_at timestamp NOT NULL,
  created_by int NOT NULL references users(id)  
);

CREATE TABLE permision_type(
	id SERIAL PRIMARY KEY,
	type varchar(10)	
);

CREATE TABLE user_in_room(
	user_id int references users(id),
	room_id int references room(id),
	joined_at timestamp NOT NULL,
	permision_id int references permision_type(id)

);