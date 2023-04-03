-- Table: User
-- Goals: To store an info about User
-- Fields: id, name, password
create table if not exists certificate.user
(
    id       bigserial primary key not null,
    name     varchar(255),
    password varchar(255),
    role_id  int4                  not null
);
