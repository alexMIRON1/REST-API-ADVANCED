-- Table: Tag
-- Goals: To store an info about Tag
-- Fields: id, name
create table if not exists certificate.tag
(
    id   bigserial primary key not null,
    name varchar(255)
);