DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id   serial primary key,
    name varchar(50) not null,
    age  int         not null
);