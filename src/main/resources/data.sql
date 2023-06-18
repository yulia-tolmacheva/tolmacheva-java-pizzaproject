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

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Dough', true, false, false);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Dough Gluten Free', true, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Tomato Sauce', true, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Mozzarella Cheese', true, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Pepperoni', false, true, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Mushrooms', true, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Onions', true, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Black Olives', true, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Sausage', false, true, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Oregano', true, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Parmesan Cheese', true, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Ham', false, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Pineapple', true, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Artichokes', true, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Anchovies', false, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Red Peppers', true, true, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Salami', false, false, true);

INSERT INTO ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
VALUES ('Chicken', false, false, true);

-- insert into pizzas (name, size, price, restaurant_id)
-- values ('Pizza1', 'SMALL', 10.99, 1);
-- insert into pizzas (name, size, price, restaurant_id)
-- values ('Pizza2', 'SMALL', 10.99, 1);
-- insert into pizzas (name, size, price, restaurant_id)
-- values ('Pizza3', 'MEDIUM', 12.99, 2);
-- insert into pizzas (name, size, price, restaurant_id)
-- values ('Pizza4', 'MEDIUM', 10.99, 3);
-- insert into pizzas (name, size, price, restaurant_id)
-- values ('Pizza5', 'LARGE', 12.99, 1);

INSERT INTO pizzas (name, size, price, restaurant_id)
VALUES
-- Restaurant 1
('Margherita', 'SMALL', 10.99, 1),
('Pepperoni', 'MEDIUM', 12.99, 1),
('Vegetarian', 'LARGE', 14.99, 1),
('Hawaiian', 'SMALL', 10.99, 1),
('Meat Lovers', 'MEDIUM', 12.99, 1),
('Caprese', 'LARGE', 14.99, 1),
('Quattro Formaggi', 'SMALL', 10.99, 1),
('BBQ Chicken', 'MEDIUM', 12.99, 1),
('Mushroom Truffle', 'LARGE', 14.99, 1),
('Veggie Supreme', 'SMALL', 10.99, 1),

-- Restaurant 2
('Margherita', 'SMALL', 10.99, 2),
('Pepperoni', 'MEDIUM', 12.99, 2),
('Vegetarian', 'LARGE', 14.99, 2),
('Hawaiian', 'SMALL', 10.99, 2),
('Meat Lovers', 'MEDIUM', 12.99, 2),
('Caprese', 'LARGE', 14.99, 2),
('Quattro Formaggi', 'SMALL', 10.99, 2),
('BBQ Chicken', 'MEDIUM', 12.99, 2),
('Mushroom Truffle', 'LARGE', 14.99, 2),
('Veggie Supreme', 'SMALL', 10.99, 2),

-- Restaurant 3
('Classic Cheese', 'SMALL', 9.99, 3),
('Supreme', 'MEDIUM', 12.99, 3),
('Veggie Delight', 'LARGE', 14.99, 3),
('Pepperoni Lover', 'SMALL', 11.99, 3),
('Meat Feast', 'MEDIUM', 13.99, 3),
('BBQ Chicken Ranch', 'LARGE', 15.99, 3),
('Hawaiian', 'SMALL', 10.99, 3),
('Mushroom Madness', 'MEDIUM', 12.99, 3),
('Spicy Sausage', 'LARGE', 14.99, 3),
('Margherita', 'SMALL', 9.99, 3),

-- Restaurant 4
('Classic Pepperoni', 'SMALL', 10.99, 4),
('Veggie Supreme', 'MEDIUM', 13.99, 4),
('Meat Lovers', 'LARGE', 15.99, 4),
('Margherita', 'SMALL', 9.99, 4),
('Chicken BBQ', 'MEDIUM', 12.99, 4),
('Four Cheese', 'LARGE', 14.99, 4),
('Hawaiian', 'SMALL', 10.99, 4),
('Mushroom Madness', 'MEDIUM', 12.99, 4),
('Spicy Sausage', 'LARGE', 14.99, 4),
('Pepperoni Supreme', 'SMALL', 10.99, 4),

-- Restaurant 5
('Classic Margherita', 'SMALL', 9.99, 5),
('BBQ Chicken', 'MEDIUM', 12.99, 5),
('Supreme Deluxe', 'LARGE', 15.99, 5),
('Pepperoni Passion', 'SMALL', 10.99, 5),
('Meat Feast', 'MEDIUM', 13.99, 5),
('Veggie Paradise', 'LARGE', 14.99, 5),
('Hawaiian', 'SMALL', 10.99, 5),
('Mushroom Magic', 'MEDIUM', 12.99, 5),
('Spicy Sausage', 'LARGE', 14.99, 5),
('Cheese Lover', 'SMALL', 9.99, 5);

-- Pizza 1 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (1, 1), (1, 3), (1, 4);

-- Pizza 2 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (2, 1), (2, 3), (2, 4), (2, 5);

-- Pizza 3 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (3, 1), (3, 3), (3, 4), (3, 6), (3, 7);

-- Pizza 4 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (4, 2), (4, 3), (4, 4), (4, 5), (4, 8);

-- Pizza 5 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (5, 2), (5, 3), (5, 4), (5, 6), (5, 8);

-- Pizza 6 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (6, 1), (6, 3), (6, 4), (6, 7), (6, 10);

-- Pizza 7 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (7, 1), (7, 3), (7, 4), (7, 8), (7, 9), (7, 11);

-- Pizza 8 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (8, 1), (8, 3), (8, 4), (8, 6), (8, 8), (8, 12);

-- Pizza 9 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (9, 1), (9, 3), (9, 4), (9, 7), (9, 9), (9, 10), (9, 13);

-- Pizza 10 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (10, 1), (10, 3), (10, 4), (10, 6), (10, 8), (10, 11), (10, 14);

-- Pizza 11 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (11, 1), (11, 3), (11, 4), (11, 5), (11, 6);

-- Pizza 12 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (12, 2), (12, 3), (12, 7), (12, 9), (12, 12), (12, 13), (12, 16);

-- Pizza 13 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (13, 1), (13, 3), (13, 4), (13, 6), (13, 8), (13, 11), (13, 14), (13, 15), (13, 17);

-- Pizza 14 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (14, 2), (14, 3), (14, 5), (14, 7), (14, 10), (14, 12), (14, 13), (14, 15), (14, 18);

-- Pizza 15 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (15, 1), (15, 3), (15, 4), (15, 5), (15, 6), (15, 8), (15, 9), (15, 10), (15, 12), (15, 14);

-- Pizza 16 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (16, 2), (16, 3), (16, 7), (16, 9), (16, 11), (16, 13), (16, 15), (16, 16), (16, 17), (16, 18);

-- Pizza 17 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (17, 1), (17, 3), (17, 4), (17, 6), (17, 8), (17, 10), (17, 11), (17, 13), (17, 15), (17, 18);

-- Pizza 18 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (18, 2), (18, 3), (18, 5), (18, 7), (18, 9), (18, 12), (18, 14), (18, 16), (18, 17), (18, 18);

-- Pizza 19 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (19, 1), (19, 3), (19, 4), (19, 6), (19, 8), (19, 10), (19, 11), (19, 13), (19, 15), (19, 17), (19, 18);

-- Pizza 20 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (20, 2), (20, 3), (20, 5), (20, 7), (20, 9);


-- insert into pizzas_ingredients
-- values (1, 1);
-- insert into pizzas_ingredients
-- values (2, 1);
-- insert into pizzas_ingredients
-- values (3, 5);
-- insert into pizzas_ingredients
-- values (4, 1);
-- insert into pizzas_ingredients
-- values (5, 1);
-- insert into pizzas_ingredients
-- values (1, 2);
-- insert into pizzas_ingredients
-- values (2, 2);
-- insert into pizzas_ingredients
-- values (3, 2);
-- insert into pizzas_ingredients
-- values (4, 2);
-- insert into pizzas_ingredients
-- values (5, 2);
-- insert into pizzas_ingredients
-- values (1, 3);
-- insert into pizzas_ingredients
-- values (2, 4);

-- insert into users (username, password)
-- values ('a', '$2a$10$mh8GJtH3bYmM9Ai4WzoE0eHSEa9Xai37cYOhGvmOM6Ay/HzPF/aam');

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
