--liquibase formatted sql
--changeset junior:202401121802
--comment: insert clients

INSERT INTO CLIENTS
(id, name)
values
(1, 'João'),
(2, 'Maria'),
(3, 'Lucas'),
(4, 'Allan'),
(5, 'Julia'),
(6, 'Renata'),
(7, 'Cristina'),
(8, 'Leandro'),
(9, 'Marcos'),
(10, 'Paulo');

--rollback DELETE FROM CLIENTS;
