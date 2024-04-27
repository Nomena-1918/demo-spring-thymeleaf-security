create table roles(
    id serial primary key,
    role varchar(50) not null
);

create table role_hierarchy(
    parent_id integer not null references roles,
    child_id  integer not null references roles,
    constraint role_hierarchy_check check (parent_id <> child_id)
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

CREATE EXTENSION IF NOT EXISTS pgcrypto;


-- insert data
insert into roles(role) values('ROLE_USER');
insert into roles(role) values('ROLE_ADMIN');
/*
 INSERT INTO users (username, password) VALUES (
  'user',
  crypt('password', gen_salt('bf'))
);
 SELECT id
FROM users
WHERE username = 'user'
  AND password = crypt('password', password);

 */
INSERT INTO users (username, password) VALUES
('user',crypt('password', gen_salt('bf')));
INSERT INTO users (username, password) VALUES
('admin',crypt('password', gen_salt('bf')));

insert into user_roles(user_id, role_id) values(1, 1);
insert into user_roles(user_id, role_id) values(2, 2);

insert into role_hierarchy(parent_id, child_id) values(2, 1);









