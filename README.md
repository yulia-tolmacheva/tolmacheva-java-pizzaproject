# Backend for Pizzerias

The pizza project functionality includes the ability to create, read, update, and delete (CRUD) records for pizzas, restaurants, pizza's ingredients, users with multiple roles.

### Added filtering:
- for pizzas: by pizza's ingredients, sizes, 3 types depends on their ingredients (vegetarian, spicy, gluten free) or restaurants (just for admin role)
- for ingredients: ny name (just for admin role);
- for restaurants: by city and address;
- for users: by username (just for admin role);

### Security (url-secuirty, role-based and permission-based)
The functionality is secured with Spring Security 6 by requiring a username of "admin" to access all features and restricted for others.
- Everyone can see restaurants list and their pizzas, use filters
- All users with admin-role are able to add and delete restaurants;
- All users with admin-role or restaurant owner (owner role assigned to the particular restaurant) are able to edit restaurants details or their pizzas;
- All users with admin-role are able to add, edit, delete users;
- Sign up as lowest User role is possible;
- All users with admin-role are able to add, edit, delete ingredients;

## Technologies

#### Java 17
#### Spring Boot 3
#### Spring Security 6
#### Thymeleaf and CSS frontend
#### Mockito/JUnit (testing)
#### PostgreSQL
#### H2 database (testing)
