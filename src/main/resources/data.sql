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

INSERT INTO user_role (role, user_id)
VALUES ('ADMIN', 1),
       ('USER', 1),
       ('USER', 2),
       ('USER', 3);

INSERT INTO RESTAURANT (name, date)
VALUES  ('Tokyo City', CURRENT_DATE),
        ('Afterlife', CURRENT_DATE);

INSERT INTO DISH (restaurant_id, name, price)
VALUES (1, 'Том Ям', 800),
       (1, 'Пепперони', 500),
       (2, 'Джонни Сильверхенд', 450),
       (2, 'Bloody Marry', 550);

