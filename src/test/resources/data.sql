INSERT INTO restaurants (name, city, address, phone)
VALUES ('Pizzeria Bella', 'Berlin', 'Hauptstraße 1', '+49 30 12345678');

INSERT INTO restaurants (name, city, address, phone)
VALUES ('La Pizzetta', 'Frankfurt', 'Kaiserstraße 10', '+49 69 98765432');


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


INSERT INTO pizzas (name, size, price, restaurant_id)
VALUES
-- Restaurant 1
('Margherita', 'SMALL', 10.99, 1),
('Pepperoni', 'MEDIUM', 12.99, 1),
('Vegetarian', 'LARGE', 14.99, 1),
('Hawaiian', 'SMALL', 10.99, 1),

-- Restaurant 2
('Margherita', 'SMALL', 10.99, 2),
('Pepperoni', 'MEDIUM', 12.99, 2),
('Vegetarian', 'LARGE', 14.99, 2),
('Hawaiian', 'SMALL', 10.99, 2);

-- Pizza 1 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (1, 1), (1, 3), (1, 4);

-- Pizza 2 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (2, 1), (2, 3), (2, 4), (2, 5);

-- Pizza 3 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (3, 1), (3, 3), (3, 6);

-- Pizza 4 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (4, 2), (4, 3), (4, 4), (4, 5);

-- Pizza 5 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (5, 2), (5, 3), (5, 4), (5, 6);

-- Pizza 6 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (6, 1), (6, 3), (6, 4);

-- Pizza 7 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (7, 1), (7, 3), (7, 4);

-- Pizza 8 Ingredients
INSERT INTO pizzas_ingredients (pizza_id, ingredient_id)
VALUES (8, 1), (8, 3), (8, 4), (8, 6);