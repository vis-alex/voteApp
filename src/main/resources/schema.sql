DROP TABLE votes IF EXISTS;
DROP TABLE dishes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;

DROP SEQUENCE SEQ IF EXISTS;

CREATE SEQUENCE SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
    id       INTEGER GENERATED BY DEFAULT AS SEQUENCE SEQ PRIMARY KEY,
    name     VARCHAR(255)         NOT NULL,
    password VARCHAR(255)         NOT NULL,
    enabled  BOOLEAN DEFAULT TRUE NOT NULL
);
CREATE UNIQUE INDEX users_unique_name_idx ON users (name);

CREATE TABLE user_roles
(
    user_id  INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE dishes
(
    id               INTEGER GENERATED BY DEFAULT AS SEQUENCE SEQ PRIMARY KEY,
    name             VARCHAR(255)            NOT NULL,
    price            INTEGER                 NOT NULL,
    restaurant_id    INTEGER                 NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX dishes_unique_restaurant_id_idx ON dishes (name, restaurant_id);

CREATE TABLE votes
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE SEQ PRIMARY KEY,
    user_id       INTEGER                 NOT NULL,
    restaurant_id INTEGER                 NOT NULL,
    date_time     TIMESTAMP default now() NOT NULL,
    CONSTRAINT user_vote_time_idx UNIQUE (user_id, date_time)
);
CREATE INDEX votes_restaurant_id_idx ON votes (restaurant_id);
CREATE INDEX votes_user_id_idx ON votes (user_id);
