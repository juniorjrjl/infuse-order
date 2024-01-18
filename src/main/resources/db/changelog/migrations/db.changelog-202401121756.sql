--liquibase formatted sql
--changeset junior:202401121756
--comment: orders_items create table

CREATE TABLE ORDERS_ITEMS (
    order_id bigint not null,
    item_id bigint not null,
    amount bigint not null,
    constraint fk_orders_orders_items foreign key (order_id) references ORDERS(id),
    constraint fk_items_orders_items foreign key (item_id) references ITEMS(id),
    primary key(order_id, item_id)
)engine=InnoDB default charset=utf8;

--rollback DROP TABLE ORDERS_ITEMS;
