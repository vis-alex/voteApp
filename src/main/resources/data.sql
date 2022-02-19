DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;

-- Пароли 123 234 345
INSERT INTO users ( name, password)
VALUES ( 'Vik', '$2a$12$jnEM6Yxp/1AdPTe6OVjgUe3kLwdWfsaeAfE4D.u/XXoaHf0GWuaEW'),
       ( 'Nik', '$2a$12$Xd8vA3zBR9Y7pfA7ITf/reYON4QHO4iIZaNQuj8byMEuqSzC.IjDK'),
       ( 'Dick', '$2a$12$k17SyXEalp1GVWnh/kWkN.vY8y78aeTNGCyMGAcTMH6ThsoxHVhhC');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001),
       ('USER', 100001),
       ('USER', 100002);

INSERT INTO restaurants (name)
VALUES ('Shoko'),
       ('Japan'),
       ('BurgerKing');

INSERT INTO dishes (name, price, restaurant_id)
VALUES ('Soup', 100, 100003),
       ('Water', 200, 100003),
       ('Bread', 300, 100004),
       ('Muss', 400, 100005);