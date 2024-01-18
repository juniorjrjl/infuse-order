--liquibase formatted sql
--changeset junior:202401141134
--comment: add unique key to orders table

ALTER TABLE ORDERS ADD CONSTRAINT control_id_uk UNIQUE (control_id);

--rollback ALTER TABLE ORDERS DROP INDEX control_id_uk;