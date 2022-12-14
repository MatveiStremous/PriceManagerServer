create table if not exists user
(
    user_id  int auto_increment
        primary key,
    login    varchar(40)  not null,
    password varchar(100) not null,
    role     varchar(20)  not null,
    status   varchar(20)  null,
    constraint user_login_uindex
        unique (login)
);

create table if not exists company
(
    company_id         int auto_increment
        primary key,
    name               varchar(30)      not null,
    balance            double default 0 null,
    user_id            int              null,
    amount_of_products int    default 0 null,
    constraint company_name_uindex
        unique (name),
    constraint company_user_user_id_fk
        foreign key (user_id) references user (user_id)
            on delete cascade
);

create table if not exists cost_calculation
(
    calc_id     int auto_increment
        primary key,
    materials   double null,
    production  double null,
    deprecation double null,
    salary      double null,
    others      double null,
    user_id     int    not null,
    constraint cost_calculation_user_user_id_fk
        foreign key (user_id) references user (user_id)
            on delete cascade
);

create table if not exists price_calculation
(
    calc_id       int auto_increment
        primary key,
    average_cost  double not null,
    increase_perc float  not null,
    tax_perc      float  not null,
    user_id       int    not null,
    constraint price_calculation_user_user_id_fk
        foreign key (user_id) references user (user_id)
            on delete cascade
);

create table if not exists product
(
    product_id            int auto_increment
        primary key,
    name                  varchar(30)      null,
    amount                bigint default 0 null,
    average_cost          double default 0 null,
    average_selling_price double default 0 null,
    company_id            int              not null,
    constraint product_company_company_id_fk
        foreign key (company_id) references company (company_id)
            on delete cascade
);

create table if not exists production
(
    production_id int auto_increment
        primary key,
    amount        bigint                     null,
    total_costs   double default 0           null,
    date          date   default (curdate()) null,
    product_id    int                        not null,
    constraint production_product_product_id_fk
        foreign key (product_id) references product (product_id)
            on delete cascade
);

create table if not exists sale
(
    sale_id     int auto_increment
        primary key,
    amount      bigint default 0           not null,
    total_price double default 0           not null,
    date        date   default (curdate()) not null,
    product_id  int                        not null,
    constraint sale_product_product_id_fk
        foreign key (product_id) references product (product_id)
            on delete cascade
);
