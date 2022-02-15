DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;

INSERT INTO users (id, name, password)
VALUES (1, 'Vik', '123'),
       (3, 'Nik', '234'),
       (2, 'Dick', '345');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);