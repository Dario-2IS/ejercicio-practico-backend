-- 1. Crear la base de datos
CREATE DATABASE eval;

-- Cambiar al contexto de la base de datos eval

-- 2. Crear tabla Person
CREATE TABLE person (
    id SERIAL PRIMARY KEY,
    identification_number VARCHAR(50) NOT NULL UNIQUE,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    gender BOOLEAN NOT NULL,
    age INTEGER NOT NULL,
    phone_number VARCHAR(20),
    address TEXT
);

-- 3. Crear tabla Client
CREATE TABLE client (
    id INTEGER PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    state BOOLEAN NOT NULL,
    FOREIGN KEY (id) REFERENCES person(id) ON DELETE CASCADE
);

-- 4. Crear tabla Account
CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type VARCHAR(50) NOT NULL,
    balance DOUBLE PRECISION NOT NULL,
    currency VARCHAR(10) NOT NULL,
    state BOOLEAN NOT NULL,
    client_id INTEGER NOT NULL,
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);

-- 5. Crear tabla Transaction
CREATE TABLE transaction (
    id SERIAL PRIMARY KEY,
    transaction_type VARCHAR(50) NOT NULL,
    initial_balance DOUBLE PRECISION NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    currency VARCHAR(10) NOT NULL,
    final_balance DOUBLE PRECISION NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    account_id INTEGER NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);