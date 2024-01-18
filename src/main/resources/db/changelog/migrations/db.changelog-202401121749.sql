--liquibase formatted sql
--changeset junior:202401121749
--comment: clients create table

CREATE TABLE CLIENTS (
    id bigint not null auto_increment,
    name varchar(150) not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key(id)
)engine=InnoDB default charset=utf8;

--rollback DROP TABLE CLIENTS;
