# Backend for Pizzerias

The pizza project functionality includes the ability to create, read, update, and delete (CRUD) records for pizzas, restaurants, pizza's ingredients, users with multiple roles.

### Added filtering:
- for pizzas: by pizza's ingredients, sizes, 3 types depends on their ingredients (vegetarian, spicy, gluten free) or restaurants (just for admin role)
- for ingredients: ny name (just for admin role);
- for restaurants: by city and address;
- for users: by username (just for admin role);

### Security (url-secuirty, role-based and permission-based)

- Everyone can see all restaurants and the pizzas, use filters;
- All users with admin-role are able to add new and delete restaurants;
- All users with admin-role and restaurant owner (owner role assigned to the particular restaurant) are able to update restaurants details or their pizzas (create, delete, update);
- All users with admin-role are able to add, update, delete users;
- Sign up with lowest User role is possible; Other roles are only assignable by admins;
- All users with admin-role are able to add, update, delete ingredients;
- All users can change their details and passwords;

## Technologies

#### Java 17
#### Spring Boot 3
#### Spring Security 6
#### Thymeleaf and CSS frontend
#### Mockito/JUnit (testing)
#### PostgreSQL
#### H2 database (testing)
