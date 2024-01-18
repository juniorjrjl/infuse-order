--liquibase formatted sql
--changeset junior:202401121756
--comment: add external_created_at to orders table

ALTER TABLE ORDERS ADD COLUMN external_created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP AFTER client_id;

--rollback ALTER TABLE ORDERS DROP COLUMN external_created_at;