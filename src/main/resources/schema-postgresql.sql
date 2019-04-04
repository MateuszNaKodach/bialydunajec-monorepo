
CREATE SCHEMA IF NOT EXISTS camp_registrations;
CREATE SCHEMA IF NOT EXISTS camp_edition;
CREATE SCHEMA IF NOT EXISTS academic_ministry;
CREATE SCHEMA IF NOT EXISTS users;
CREATE SCHEMA IF NOT EXISTS email;
CREATE TABLE IF NOT EXISTS oauth_access_token
(
    token_id          VARCHAR(255) NOT NULL,
    token             BYTEA,
    authentication_id VARCHAR(255) PRIMARY KEY,
    user_name         VARCHAR(255),
    client_id         VARCHAR(255),
    authentication    BYTEA,
    refresh_token     VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS oauth_refresh_token
(
    token_id       VARCHAR(255),
    token          BYTEA,
    authentication BYTEA
);