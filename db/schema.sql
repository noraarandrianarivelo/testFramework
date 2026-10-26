CREATE TABLE IF NOT EXISTS produit (
    id    SERIAL PRIMARY KEY,
    nom   VARCHAR(100) NOT NULL,
    prix  NUMERIC(10, 2) NOT NULL
);

INSERT INTO produit (nom, prix) VALUES
    ('Objet 1', 1000),
    ('Objet 2', 2500),
    ('Objet 3', 1200),
    ('Objet 4', 3000);

CREATE TABLE IF NOT EXISTS utilisateur (
    id       SERIAL PRIMARY KEY,
    username      VARCHAR(100) NOT NULL,
    password    VARCHAR(100) NOT NULL UNIQUE
);

ALTER TABLE utilisateur ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'user';

INSERT INTO utilisateur (username, password, role) VALUES
    ('user', 'user', 'user'),
    ('client', 'client', 'client'),
    ('finance', 'finance', 'finance');