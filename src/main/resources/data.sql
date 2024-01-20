DROP TABLE IF EXISTS shedlock;

CREATE TABLE shedlock(
                         name VARCHAR(64) NOT NULL,
                         lock_until TIMESTAMP(3) NOT NULL,
                         locked_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                         locked_by VARCHAR(255) NOT NULL,
                         PRIMARY KEY (name)
);

INSERT INTO users (name, email, password)
VALUES  ('Admin', 'admin@gmail.com', '{noop}admin'),
        ('User1', 'user1@yandex.ru', '{noop}password'),
        ('User2', 'user2@gmail.com', '{noop}password'),
        ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO users (id,name, email, password)
VALUES  (0 ,'deleted', 'deleted@gmail.com', '{noop}deleted');

INSERT INTO user_role (role, user_id)
VALUES ('ADMIN', 1),
       ('USER', 1),
       ('USER', 2),
       ('USER', 3);

INSERT INTO RESTAURANT (name)
VALUES  ('Tokyo City'),
        ('Afterlife'),
        ('Titanic 2000');

INSERT INTO DISH (name, price)
VALUES ('Том Ям', 800),
       ('Пепперони', 500),
       ('Джонни Сильверхенд', 450),
       ('Bloody Marry', 550);

INSERT INTO MENU (date, restaurant_id)
VALUES  (CURRENT_DATE, 1),
        (CURRENT_DATE, 2)


