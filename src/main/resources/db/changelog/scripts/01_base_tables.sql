--liquibase formatted sql
--changeset roba:1

CREATE TABLE income (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR NOT NULL,
    amount_in_cents INT NOT NULL,
    due_date TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE expense (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR NOT NULL,
    amount_in_cents INT NOT NULL,
    due_date TIMESTAMP WITH TIME ZONE NOT NULL,
    type VARCHAR NOT NULL
);

CREATE INDEX income_due_date_idx on income(due_date);
CREATE INDEX expenses_due_date_idx on expense(due_date);