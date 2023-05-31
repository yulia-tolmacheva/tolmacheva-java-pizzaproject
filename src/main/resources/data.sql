insert into restaurants (name, address, phone)
values ('restaurant1', 'Berlin', 54683838);
insert into restaurants (name, address, phone)
values ('restaurant2', 'Berlin', 54683838);
insert into restaurants (name, address, phone)
values ('restaurant3', 'Berlin', 54683838);
insert into restaurants (name, address, phone)
values ('restaurant4', 'Berlin', 54683838);

insert into ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
values ('Dough', true, false, false);
insert into ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
values ('Tomato sauce', false, false, true);
insert into ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
values ('Cheese', true, false, true);
insert into ingredients (name, is_vegetarian, is_spicy, is_glutenfree)
values ('Salami', false, true, true);

insert into pizzas (name, size, price, ingredients, restaurant_id)
values ('Pizza1', 14, 10.99, 1, 1);
insert into pizzas (name, size, price, ingredients, restaurant_id)
values ('Pizza2', 14, 10.99, 1, 1);
insert into pizzas (name, size, price, ingredients, restaurant_id)
values ('Pizza3', 15, 12.99, 1, 1);
insert into pizzas (name, size, price, ingredients, restaurant_id)
values ('Pizza4', 14, 10.99, 1, 1);
insert into pizzas (name, size, price, ingredients, restaurant_id)
values ('Pizza5', 15, 12.99, 1, 1);

insert into users (name, password, access_rights)
values ('admin', 'admin', 'admin');
insert into users (name, password, access_rights)
values ('user1', 'user1', 'user');