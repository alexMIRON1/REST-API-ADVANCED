-- Table: Role
-- Goals: To store roles
-- Fields: id, name
create table if not exists certificate.role
(
    id   bigserial primary key not null,
    name varchar(255)
);