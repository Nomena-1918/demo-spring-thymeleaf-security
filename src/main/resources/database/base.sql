create table users(
    id serial primary key,
    username varchar(50) not null unique,
    password varchar(100) not null,
    role varchar(50) not null
);






