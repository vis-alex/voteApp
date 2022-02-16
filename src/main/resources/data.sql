DELETE FROM user_roles;
DELETE FROM votes;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM users;

INSERT INTO users ( name, password)
VALUES ( 'Vik', '123'),
       ( 'Nik', '234'),
       ( 'Dick', '345');

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