--liquibase formatted sql
--changeset junior:202401121753
--comment: orders create table

CREATE TABLE ORDERS (
    id bigint not null auto_increment,
    control_id bigint not null,
    discount decimal(15,2) not null,
    client_id bigint not null,
    sub_total decimal(15,2) not null,
    total decimal(15,2) not null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint fk_orders_clients foreign key (client_id) references CLIENTS(id),
    primary key(id)
)engine=InnoDB default charset=utf8;

--rollback DROP TABLE ORDERS;
