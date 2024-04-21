create table roles(
    id serial primary key,
    role varchar(50) not null
);

create table users(
    id serial primary key,
    username varchar(50) not null unique,
    password varchar(100) not null
);

create table user_roles(
    user_id int not null,
    role_id int not null,
    primary key(user_id, role_id),
    foreign key(user_id) references users(id),
    foreign key(role_id) references roles(id)
);







