-- Table: Order
-- Goals: To store an info about Order
-- Fields: id, price, purchase time, gift certificate id, user id
create table if not exists certificate.order
(
    id bigserial primary key not null,
    price numeric(19,2),
    purchase_time timestamp,
    gift_certificate_id int8,
    user_id int8
);