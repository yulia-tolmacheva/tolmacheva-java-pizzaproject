INSERT INTO restaurants (name, city, address, phone)
VALUES ('Pizzeria Bella', 'Berlin', 'Hauptstraße 1', '+49 30 12345678');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('La Pizzetta', 'Frankfurt', 'Kaiserstraße 10', '+49 69 98765432');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Pizza Express', 'Dresden', 'Altmarkt 5', '+49 351 24681357');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Pizzeria Napoli', 'Munich', 'Marienplatz 15', '+49 89 74185263');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Il Forno', 'Hamburg', 'Reeperbahn 20', '+49 40 369258147');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Pizza World', 'Berlin', 'Friedrichstraße 50', '+49 30 57293048');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Pizzeria Roma', 'Frankfurt', 'Leipziger Straße 35', '+49 69 94857632');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Da Mario', 'Dresden', 'Prager Straße 60', '+49 351 24680257');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Pizza Amore', 'Munich', 'Schwabing 25', '+49 89 68274059');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('La Piazza', 'Hamburg', 'Jungfernstieg 30', '+49 40 78593624');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Pizza Hut', 'Frankfurt', 'Zeil 12', '+49 69 1234567');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Papa Johns Pizza', 'Frankfurt', 'Schillerstraße 18', '+49 69 2345678');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Mamma Mia Pizzeria', 'Frankfurt', 'Taunusstraße 24', '+49 69 3456789');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Pizza Ristorante', 'Frankfurt', 'Sachsenhausen 40', '+49 69 4567890');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('Pizzeria Venezia', 'Frankfurt', 'Hauptwache 8', '+49 69 5678901');



insert into ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
values ('Dough', true, false, false);
insert into ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
values ('Tomato sauce', true, false, true);
insert into ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
values ('Cheese', true, false, true);
insert into ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
values ('Salami', false, true, true);
insert into ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
values ('Dough Gluten Free', true, false, true);

insert into pizzas (name, size, price, restaurant_id)
values ('Pizza1', 'SMALL', 10.99, 1);
insert into pizzas (name, size, price, restaurant_id)
values ('Pizza2', 'SMALL', 10.99, 1);
insert into pizzas (name, size, price, restaurant_id)
values ('Pizza3', 'MEDIUM', 12.99, 2);
insert into pizzas (name, size, price, restaurant_id)
values ('Pizza4', 'MEDIUM', 10.99, 3);
insert into pizzas (name, size, price, restaurant_id)
values ('Pizza5', 'LARGE', 12.99, 1);

insert into pizzas_ingredients values (1, 1);
insert into pizzas_ingredients values (2, 1);
insert into pizzas_ingredients values (3, 5);
insert into pizzas_ingredients values (4, 1);
insert into pizzas_ingredients values (5, 1);
insert into pizzas_ingredients values (1, 2);
insert into pizzas_ingredients values (2, 2);
insert into pizzas_ingredients values (3, 2);
insert into pizzas_ingredients values (4, 2);
insert into pizzas_ingredients values (5, 2);
insert into pizzas_ingredients values (1, 3);
insert into pizzas_ingredients values (2, 4);

-- insert into users (username, password, is_enabled)
-- values ('admin', '$2a$10$mh8GJtH3bYmM9Ai4WzoE0eHSEa9Xai37cYOhGvmOM6Ay/HzPF/aam', true);
-- insert into users (username, password, is_enabled)
-- values ('user1', '$2a$10$Azdm73hzgOrKK0Oq9lRF8eeWDXiGklzVZFfTsbgnWzzCqDsVgXHvy', true);
-- insert into users (username, password, is_enabled)
-- values ('patrick', '$2a$10$MkM2BT7tM8bKV/k3XkjyU.T.6K51D.JFCCzX1HVFW/mGrbqiPMIWi', true);

-- insert into roles (name) values ('ADMIN');
-- insert into roles (name) values ('USER');
-- insert into roles (name) values ('CREATOR');

-- insert into users_roles (user_id, role_id) values (1, 1);
-- insert into users_roles (user_id, role_id)  values (1, 2);
-- insert into users_roles (user_id, role_id) values (2, 2);
-- insert into users_roles (user_id, role_id) values (3, 3);
