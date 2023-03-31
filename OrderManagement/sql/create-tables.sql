create table if not exists public._user
(
    id       bigserial
    primary key,
    password varchar(255),
    role     varchar(255),
    username varchar(255)
    );

alter table public._user
    owner to postgres;

create table if not exists public.item
(
    id      bigserial
    primary key,
    name    varchar(255),
    price   double precision,
    user_id bigint
    constraint fkpgshb94jeyqnldqn4g2yqcnga
    references public._user
    );

alter table public.item
    owner to postgres;

create table if not exists public._order
(
    id             bigserial
    primary key,
    number         varchar(255),
    status         varchar(255),
    total_items    integer,
    total_payments integer,
    user_id        bigint
    constraint fkprpwig2d34d265to98qdg694e
    references public._user
    );

alter table public._order
    owner to postgres;

create table if not exists public.order_item
(
    order_id bigint not null
    constraint fkryn1sdluxcjfeu891k75myheu
    references public._order,
    item_id  bigint not null
    constraint fkija6hjjiit8dprnmvtvgdp6ru
    references public.item,
    primary key (order_id, item_id)
    );

alter table public.order_item
    owner to postgres;

create table if not exists public.payment
(
    id           bigserial
    primary key,
    number       varchar(255),
    payment_date timestamp(6),
    sum          double precision,
    user_id      bigint
    constraint fkd6k9t8rxma3qp663stbfsg5s0
    references public._user,
    order_id     bigint
    constraint fkp12cpy6idihw3gyo3sv7pfcw2
    references public._order
    );

alter table public.payment
    owner to postgres;

create table if not exists public.crud_events
(
    id       bigserial
    primary key,
    name varchar(255)
    );

alter table public.crud_events
    owner to postgres;
