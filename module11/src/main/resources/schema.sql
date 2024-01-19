DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id long auto_increment primary key,
    name varchar(50) not null,
    age int not null
);