--liquibase formatted sql
--changeset roba:1

CREATE TABLE income
(
    id              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title           VARCHAR NOT NULL,
    amount_in_cents INT     NOT NULL,
    due_date        DATE    NOT NULL
);

CREATE TABLE income_template
(
    id              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title           VARCHAR NOT NULL,
    amount_in_cents INT     NOT NULL,
    repeat_interval VARCHAR NOT NULL
);

CREATE TABLE category
(
    id                 INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name               VARCHAR NOT NULL UNIQUE,
    disabled           BOOLEAN NOT NULL,
    parent_category_id INT REFERENCES category (id)
);

CREATE TABLE budget
(
    id                             INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name                           VARCHAR                      NOT NULL UNIQUE,
    monthly_saving_amount_in_cents INT                          NOT NULL,
    total_saved_amount_in_cents    INT                          NOT NULL,
    category_id                    INT REFERENCES category (id) NOT NULL
);

CREATE TABLE budget_expense
(
    id              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title           VARCHAR,
    amount_in_cents INT                          NOT NULL,
    due_date        DATE                         NOT NULL,
    category_id     INT REFERENCES category (id) NOT NULL,
    budget_id       INT REFERENCES budget (id)   NOT NULL
);

CREATE TABLE fix_expense_template
(
    id              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title           VARCHAR                      NOT NULL UNIQUE,
    amount_in_cents INT                          NOT NULL,
    repeat_interval VARCHAR                      NOT NULL,
    category_id     INT REFERENCES category (id) NOT NULL
);

CREATE TABLE expense
(
    id              INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title           VARCHAR,
    amount_in_cents INT                          NOT NULL,
    due_date        DATE                         NOT NULL,
    type            VARCHAR                      NOT NULL,
    category_id     INT REFERENCES category (id) NOT NULL,
    budget_id       INT REFERENCES budget (id)
);

CREATE INDEX income_date_idx ON income (due_date);
CREATE INDEX category_parent_category_id_idx ON category (parent_category_id);
CREATE INDEX category_disabled_idx ON category (disabled);
CREATE INDEX expense_due_date_idx ON expense (due_date);
CREATE INDEX expense_type_idx ON expense (type);
CREATE INDEX expense_category_id_idx ON expense (category_id);
CREATE INDEX expense_budget_id_idx ON expense (budget_id);