-- DELETE FROM user_role;
-- DELETE FROM DISH;
-- DELETE FROM restaurant;
-- DELETE FROM users;
-- ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES  ('Admin', 'admin@gmail.com', '{noop}admin'),
        ('User1', 'user1@yandex.ru', '{noop}password'),
        ('User2', 'user2@gmail.com', '{noop}password'),
        ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO user_role (role, user_id)
VALUES ('ADMIN', 1),
       ('USER', 1),
       ('USER', 2),
       ('USER', 3);

INSERT INTO RESTAURANT (NAME)
VALUES  ('Tokyo City'),
        ('Afterlife');

INSERT INTO DISH (restaurant_id, name, price)
VALUES (1, 'Том Ям', 800),
       (1, 'Пепперони', 500),
       (2, 'Джонни Сильверхенд', 450),
       (2, 'Bloody Marry', 550);
