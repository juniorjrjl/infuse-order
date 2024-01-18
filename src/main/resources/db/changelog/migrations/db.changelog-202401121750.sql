--liquibase formatted sql
--changeset junior:202401121750
--comment: items create table

CREATE TABLE ITEMS (
    id bigint not null auto_increment,
    name varchar(150) not null,
    value decimal(15,2) not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    primary key(id)
)engine=InnoDB default charset=utf8;

--rollback DROP TABLE ITEMS;
