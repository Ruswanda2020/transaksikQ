-- Create table for Banner
CREATE TABLE banner (
                        id BIGSERIAL PRIMARY KEY,
                        banner_name VARCHAR(255) NOT NULL,
                        banner_image VARCHAR(255),
                        description TEXT
);

-- Create table for Role
CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

-- Create table for User
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       profile_image VARCHAR(255)
);

-- Create table for UserBalance
CREATE TABLE user_balance (
                              id BIGSERIAL PRIMARY KEY,
                              balance INTEGER NOT NULL DEFAULT 0,
                              user_id BIGINT NOT NULL UNIQUE,
                              FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create table for Service
CREATE TABLE service (
                         id BIGSERIAL PRIMARY KEY,
                         service_code VARCHAR(50) NOT NULL UNIQUE,
                         service_name VARCHAR(255) NOT NULL,
                         service_icon VARCHAR(255),
                         service_tariff INTEGER NOT NULL
);

-- Create table for Transaction
CREATE TABLE transaction (
                             id BIGSERIAL PRIMARY KEY,
                             user_id BIGINT NOT NULL,
                             invoice_number VARCHAR(255) NOT NULL UNIQUE,
                             transaction_type VARCHAR(50) NOT NULL,
                             total_amount INTEGER NOT NULL,
                             service_code VARCHAR(50),
                             service_name VARCHAR(255),
                             created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create many-to-many table for User and Role
CREATE TABLE user_role (
                           user_id BIGINT NOT NULL,
                           role_id BIGINT NOT NULL,
                           PRIMARY KEY (user_id, role_id),
                           FOREIGN KEY (user_id) REFERENCES users(id),
                           FOREIGN KEY (role_id) REFERENCES roles(id)
);
