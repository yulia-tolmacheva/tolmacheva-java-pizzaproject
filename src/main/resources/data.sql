insert into restaurants (name, address, phone)
values ('restaurant1', 'Berlin', 54683838);
insert into restaurants (name, address, phone)
values ('restaurant2', 'Frankfurt', 54683838);
insert into restaurants (name, address, phone)
values ('restaurant3', 'Dresden', 54683838);
insert into restaurants (name, address, phone)
values ('restaurant4', 'Berlin', 54683838);

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
values ('Pizza1', 12, 10.99, 1);
insert into pizzas (name, size, price, restaurant_id)
values ('Pizza2', 12, 10.99, 1);
insert into pizzas (name, size, price, restaurant_id)
values ('Pizza3', 20, 12.99, 2);
insert into pizzas (name, size, price, restaurant_id)
values ('Pizza4', 28, 10.99, 3);
insert into pizzas (name, size, price, restaurant_id)
values ('Pizza5', 28, 12.99, 1);

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

insert into users (name, password)
values ('admin', 'admin');
insert into users (name, password)
values ('user1', 'user1');

insert into roles (name) values ('admin');
insert into roles (name) values ('user');

insert into users_roles (user_id, role_id) values (1, 1);
insert into users_roles (user_id, role_id)  values (1, 2);
insert into users_roles (user_id, role_id) values (2, 1);
