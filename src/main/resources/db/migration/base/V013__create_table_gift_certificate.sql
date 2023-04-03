-- Table: Gift certificate
-- Goals: To store an info about gift certificate
-- Fields: id, create date, description, duration, last update date, name, price
create table if not exists certificate.gift_certificate
(
    id               bigserial primary key not null,
    create_date      timestamp,
    description      varchar(255),
    duration         int4,
    last_update_date timestamp,
    name             varchar(255),
    price            numeric(19, 2)
);