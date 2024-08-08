--liquibase formatted sql
--changeset roba:1

CREATE TABLE income
(
    id              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title           VARCHAR                  NOT NULL,
    amount_in_cents INT                      NOT NULL,
    due_date        TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE expense_category
(
    id              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name            VARCHAR NOT NULL UNIQUE,
    sub_category_id INT REFERENCES expense_category (id)
);

CREATE TABLE budget
(
    id                     INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name                   VARCHAR NOT NULL UNIQUE,
    saving_amount_in_cents INT     NOT NULL
);

CREATE TABLE expense
(
    id              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title           VARCHAR,
    amount_in_cents INT                                  NOT NULL,
    due_date        TIMESTAMP WITH TIME ZONE             NOT NULL,
    type            VARCHAR                              NOT NULL,
    category_id     INT REFERENCES expense_category (id) NOT NULL,
    budget_id       INT REFERENCES budget (id)
);

CREATE INDEX income_due_date_idx ON income(due_date);
CREATE INDEX expense_due_date_idx ON expense(due_date);
CREATE INDEX expense_category_id_idx ON expense(category_id);
CREATE INDEX expense_budget_id_idx ON expense(budget_id);