create table user
(
    id       int auto_increment
        primary key,
    login    varchar(40)  not null,
    password varchar(100) not null,
    role     varchar(20)  not null,
    constraint user_login_uindex
        unique (login)
);

