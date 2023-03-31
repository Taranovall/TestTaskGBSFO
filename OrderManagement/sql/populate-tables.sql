insert into _user (password, role, username)
------------login: User password Pass
values ('$2a$10$p5CqrqT43MyDeFowbBrUgeU/fVDWZ4rO7a43byJiz/Piq4/wwUyiu', 'USER', 'User'),
------------login: Manager password Pass
       ('$2a$10$JR2kGEWlB41r40PMqQCRhOcX3OtagYRp4dj6yBFVHTMtqWnf3H.HC', 'MANAGER', 'Manager');

insert into item(name, price, user_id)
values ('firstItem', 255.1, 2),
       ('secondItem', 230, 2),
       ('thirdItem', 260, 1),
       ('fourthItem', 111, 1);

insert into _order (number, status, total_items, total_payments, user_id)
values ('№1', 'CREATED', 2, 0, 1),
       ('№3', 'CREATED', 2, 0, 1),
       ('№2', 'CREATED', 2, 1, 1),
       ('№4', 'SHIPPING', 2, 2, 2);

insert into order_item (order_id, item_id)
values (1, 1),
       (1, 3),
       (2, 1),
       (2, 4),
       (3, 3),
       (3, 4),
       (4, 2),
       (4, 3);

insert into payment (number, payment_date, sum, user_id, order_id)
values ('№1', '2023-03-30 16:25:53.211000', 225, 1, 2),
       ('№4', '2023-03-30 16:58:19.287000', 351, 2, 4),
       ('№5', '2023-03-30 17:05:07.629000', 139, 2, 4);
